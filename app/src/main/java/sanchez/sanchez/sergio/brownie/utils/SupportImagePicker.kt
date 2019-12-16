package sanchez.sanchez.sergio.brownie.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import sanchez.sanchez.sergio.brownie.BuildConfig
import java.io.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


/**
 * Support Image Picker
 */
class SupportImagePicker
/**
 * Support Image Picker
 * @param appContext
 */
@Inject
constructor(
    /**
     * App Context
     */
    private val appContext: Context
) {

    /**
     * Checks if the current context has permission to access the camera.
     */
    private fun hasCameraAccess(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Checks if the androidmanifest.xml contains the given permission.
     * @param permission
     * @return
     */
    private fun appManifestContainsPermission(permission: String): Boolean {

        val pm = appContext.packageManager

        try {

            val packageInfo = pm.getPackageInfo(
                appContext.packageName,
                PackageManager.GET_PERMISSIONS
            )

            var requestedPermissions: Array<String>? = null
            if (packageInfo != null) {
                requestedPermissions = packageInfo.requestedPermissions
            }
            if (requestedPermissions == null) {
                return false
            }
            if (requestedPermissions.isNotEmpty()) {
                val requestedPermissionsList = requestedPermissions
                return requestedPermissionsList.contains(permission)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * Add Intents To List
     * @param list
     * @param intent
     * @return
     */
    private fun addIntentsToList(
        list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {

        val resInfo = appContext.packageManager
            .queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    /**
     * Get an Intent which will launch a dialog to pick an image from camera/gallery apps.
     */
    private fun getPickImageIntent(chooserTitle: String, galleryOnly: Boolean): Intent? {

        var chooserIntent: Intent? = null
        // Create Intent List
        var intentList: MutableList<Intent> = ArrayList()
        // Action Pick Intent
        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        intentList = addIntentsToList(intentList, pickIntent)

        // Check is we want gallery apps only
        if (!galleryOnly) {

            if (appContext.packageManager
                    .hasSystemFeature(PackageManager.FEATURE_CAMERA) && !appManifestContainsPermission(
                    Manifest.permission.CAMERA
                ) || hasCameraAccess()
            ) {

                val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePhotoIntent.putExtra("return-data", true)

                val tempFileToCameraPhoto = getTemporalFile(DEFAULT_REQUEST_CODE.toString())

                takePhotoIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(
                        appContext, "brownie.core.file.provider",
                        tempFileToCameraPhoto
                    )
                )

                intentList = addIntentsToList(intentList, takePhotoIntent)

            }
        }

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
                chooserTitle
            )
            chooserIntent!!.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    /**
     * Start Chooser
     * @param activity
     * @param chooserTitle
     * @param galleryOnly
     */
    private fun startChooser(activity: Activity, chooserTitle: String, galleryOnly: Boolean) {
        val chooseImageIntent = getPickImageIntent(chooserTitle, galleryOnly)
        activity.startActivityForResult(chooseImageIntent, DEFAULT_REQUEST_CODE)
    }

    /**
     * Pick Image
     * @param chooserTitle
     * @param galleryOnly
     */
    fun pickImage(activity: Activity, chooserTitle: String, galleryOnly: Boolean) {
        // Start Chooser
        startChooser(activity, chooserTitle, galleryOnly)
    }

    /**
     * Pick Image
     * @param chooserTitle
     */
    fun pickImage(activity: Activity, chooserTitle: String) {
        // Start Chooser
        startChooser(activity, chooserTitle, false)
    }

    /**
     * Get Temporal File
     * @param payload
     * @return
     */
    private fun getTemporalFile(payload: String): File {
        return File(appContext.externalCacheDir, BASE_IMAGE_NAME + payload)
    }

    /**
     * Save Pciture
     * @param bitmap
     * @param imageSuffix
     * @return
     */
    private fun savePicture(bitmap: Bitmap, imageSuffix: String): String {
        val savedImage = getTemporalFile("$imageSuffix.jpeg")
        var fos: FileOutputStream? = null
        if (savedImage.exists()) {
            savedImage.delete()
        }
        try {
            fos = FileOutputStream(savedImage.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (!bitmap.isRecycled) {
                bitmap.recycle()
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return savedImage.absolutePath
    }

    /**
     * Open File Path
     * @param filePath
     * @return
     */
    @Throws(FileNotFoundException::class)
    private fun openFilePath(filePath: Uri): InputStream? {
        return if (filePath.authority != null)
            appContext.contentResolver.openInputStream(filePath)
        else
            FileInputStream(File(filePath.toString()))

    }

    /**
     * This method is responsible for solving the rotation issue if exist. Also scale the images to
     * 1024x1024 resolution
     * @param selectedImage
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun handleSamplingAndRotationBitmap(selectedImage: Uri): Bitmap? {


        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        var imageStream: InputStream? = null
        try {
            imageStream = openFilePath(selectedImage)
            BitmapFactory.decodeStream(imageStream, null, options)
            imageStream?.close()
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false

            imageStream = openFilePath(selectedImage)
            val img = BitmapFactory.decodeStream(imageStream, null, options)
            imageStream?.close()
            // Rotate Image If Required
            return img?.let {
                rotateImageIfRequired(it, selectedImage)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            imageStream?.close()
        }

        return null

    }


    /**
     * Calculate In Sample Size
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {

        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
            val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio

            val totalPixels = (width * height).toFloat()

            // Anything more than 2x the requested pixels we'll sample down further
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
        }
        return inSampleSize
    }

    /**
     * Rotate an image if required.
     * @param img
     * @param selectedImage
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap? {
        var ei: ExifInterface? = null
        if (Build.VERSION.SDK_INT > 23) {
            openFilePath(selectedImage)?.also {
                ei = ExifInterface(it)
                it.close()
            }
        } else {
            selectedImage.path?.let {
                ei = ExifInterface(it)
            }
        }

        val imageToReturn: Bitmap?

        imageToReturn = ei?.let {

            val orientation =
                it.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            return when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90.0f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180.0f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270.0f)
                else -> img
            }
        }

        return imageToReturn

    }

    /**
     * Rote Image
     * @param img
     * @param degree
     * @return
     */
    private fun rotateImage(img: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        val rotatedImg = Bitmap.createBitmap(img, 0, 0,
            img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    /**
     * Called after launching the picker with the same values of Activity.getImageFromResult
     * in order to resolve the result and get the image path.
     * @param requestCode
     * @param resultCode
     * @param imageReturnedIntent
     * @return
     */
    @Nullable
    fun getImagePathFromResult(
        requestCode: Int, resultCode: Int,
        imageReturnedIntent: Intent?
    ): String? {

        var selectedImage: Uri? = null
        if (resultCode == Activity.RESULT_OK && requestCode == DEFAULT_REQUEST_CODE) {
            val imageFile = getTemporalFile(DEFAULT_REQUEST_CODE.toString())
            val isCamera = (imageReturnedIntent == null
                    || imageReturnedIntent.data == null
                    || imageReturnedIntent.data!!.toString().contains(imageFile.toString()))
            selectedImage = if (isCamera) {
                Uri.parse(imageFile.getAbsolutePath())
            } else {
                imageReturnedIntent!!.data
            }
        }
        if (selectedImage == null) {
            return null
        }

        return try {
            handleSamplingAndRotationBitmap(selectedImage)?.let {
                getFilePathFromBitmap(it, selectedImage.path.hashCode().toString())
            }
        } catch (e: IOException) {
            null
        }

    }

    /**
     * Get stream, save the picture to the temp file and return path.
     *
     * @param bitmap
     * @return path to the saved image.
     */
    private fun getFilePathFromBitmap(bitmap: Bitmap, suffix: String): String {
        return savePicture(bitmap, suffix)
    }

    companion object {

        /**
         * Default Request Code
         */
        const val DEFAULT_REQUEST_CODE = 213
        const val MAX_WIDTH = 400        // min pixels
        const val MAX_HEIGHT = 400
        const val BASE_IMAGE_NAME = "i_prefix_"
    }


}