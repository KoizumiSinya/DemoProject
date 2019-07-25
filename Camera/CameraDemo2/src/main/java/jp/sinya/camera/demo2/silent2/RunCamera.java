package jp.sinya.camera.demo2.silent2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.util.Range;
import android.view.Surface;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class RunCamera extends Service {
    private static final String TAG = "Sinya";

    public RunCamera() {
    }

    private ImageReader mImageReader;

    private MediaRecorder mediaRecorder;
    private final CameraCaptureSessionStateCallback cameraCaptureSessionStateCallback = new CameraCaptureSessionStateCallback();
    private final CameraDeviceStateCallback cameraDeviceStateCallback = new CameraDeviceStateCallback();
    private CameraDevice cameraDevice;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            // Use this to initialize the camera profile
            setupCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Use this to start recording video
        start();
    }

    @Override
    public void onDestroy() {
        stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public void setupCamera() throws IOException {
        try {
            mImageReader = ImageReader.newInstance(720, 1280, ImageFormat.JPEG, /*maxImages*/2);

//            mediaRecorder = new MediaRecorder();
//            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
//            final String filename = "/storage/emulated/0/" + System.currentTimeMillis() + ".mp4";
//
//
//            CamcorderProfile profile = CamcorderProfile.get(0, CamcorderProfile.QUALITY_HIGH);
//            mediaRecorder.setOutputFile(filename);
//            mediaRecorder.setOrientationHint(0);
//            mediaRecorder.setProfile(profile);
//            mediaRecorder.prepare();
        } catch (Exception e) {
            Log.d(TAG, "start: exception" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void start() {
        // This allocates and starts all camera and recording proccesses
        Log.d(TAG, "start: ");
        CameraManager cameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        try {
            // The "1" indicates the front camera, 0 would be back and other values change for different devices
            cameraManager.openCamera("0", cameraDeviceStateCallback, handler);
        } catch (CameraAccessException | SecurityException e) {
            Log.d(TAG, "start: exception " + e.getMessage());
        }

    }

    public void stop() {
        // This stops and release all camera and recording proccesses
        Log.d(TAG, "stop: ");
//        if (mediaRecorder != null) {
//            mediaRecorder.stop();
//            mediaRecorder.reset();
//            mediaRecorder.release();
//            mediaRecorder = null;
//        }
        if (mImageReader != null) {
            mImageReader = null;
        }
        cameraDevice.close();
    }

    // Callbacks used to init camera, and set some parameters
    private class CameraCaptureSessionStateCallback extends CameraCaptureSession.StateCallback {
        private final static String TAG = "CamCaptSessionStCb";

        @Override
        public void onActive(CameraCaptureSession session) {
            Log.d(TAG, "onActive: ");
            super.onActive(session);
        }

        @Override
        public void onClosed(CameraCaptureSession session) {
            Log.d(TAG, "onClosed: ");
            super.onClosed(session);
        }

        @Override
        public void onConfigured(CameraCaptureSession session) {
            Log.d(TAG, "onConfigured: ");
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
            Log.d(TAG, "onConfigureFailed: ");
        }

        @Override
        public void onReady(CameraCaptureSession session) {
            Log.d(TAG, "onReady: ");
            super.onReady(session);
            //saveRecord(session);
            saveImage(session);
        }

        @Override
        public void onSurfacePrepared(CameraCaptureSession session, Surface surface) {
            Log.d(TAG, "onSurfacePrepared: ");
            super.onSurfacePrepared(session, surface);
        }
    }

    private void saveRecord(CameraCaptureSession session) {
        try {
            // This makes the camera ready for recording
            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);

            // Two lines right here set the FPS of the recording, used if you want high quality (60fps), slow mo (120fps+) or just more stable FPS recording
            Range<Integer> fpsRange = Range.create(30, 30);
            builder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fpsRange);

            builder.addTarget(mediaRecorder.getSurface());
            CaptureRequest request = builder.build();
            session.setRepeatingRequest(request, null, handler);
            mediaRecorder.start();
        } catch (CameraAccessException e) {
            Log.d(TAG, "onConfigured: " + e.getMessage());
        }
    }


    private void saveImage(CameraCaptureSession session) {
        mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, handler);

        try {
            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            builder.addTarget(mImageReader.getSurface());
            builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            builder.set(CaptureRequest.JPEG_ORIENTATION, 0);

            CameraCaptureSession.CaptureCallback CaptureCallback = new CameraCaptureSession.CaptureCallback() {

                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                }
            };

            session.stopRepeating();
            session.abortCaptures();
            session.capture(builder.build(), CaptureCallback, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


    }

    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            handler.post(new ImageSaver(reader.acquireNextImage()));
        }
    };

    // Another callback for the camera
    private class CameraDeviceStateCallback extends CameraDevice.StateCallback {
        private final static String TAG = "CamDeviceStateCb";

        @Override
        public void onClosed(CameraDevice camera) {
            Log.d(TAG, "onClosed: ");
            super.onClosed(camera);
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            Log.d(TAG, "onDisconnected: ");
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            Log.d(TAG, "onError: ");
        }

        @Override
        public void onOpened(CameraDevice camera) {
            Log.d(TAG, "onOpened: ");
            cameraDevice = camera;
            try {
                //camera.createCaptureSession(Arrays.asList(mediaRecorder.getSurface()), cameraCaptureSessionStateCallback, handler);
                camera.createCaptureSession(Arrays.asList(mImageReader.getSurface()), cameraCaptureSessionStateCallback, handler);
            } catch (CameraAccessException e) {
                Log.d(TAG, "onOpened: " + e.getMessage());
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class ImageSaver implements Runnable {

        /**
         * The JPEG image
         */
        private final Image mImage;
        /**
         * The file we save the image into.
         */
        private final File mFile;

        ImageSaver(Image image) {
            mImage = image;
            mFile = new File("/storage/emulated/0/" + System.currentTimeMillis() + ".jpg");
        }

        @Override
        public void run() {
            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(mFile);
                output.write(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mImage.close();
                if (null != output) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}