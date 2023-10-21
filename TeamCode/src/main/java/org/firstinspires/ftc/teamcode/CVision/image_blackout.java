package org.firstinspires.ftc.teamcode.CVision;

import android.app.assist.AssistStructure;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraBase;

public class image_blackout extends AppCompatActivity {

    private Mat mRgba;

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
    }
    public Mat onCameraFrame(Mat inputFrame) {
        inputFrame.copyTo(mRgba);
        return mRgba;
    }

    private void captureBitmap(){
        ImageView mBitmap = new ImageView(getBaseContext());
        Bitmap bitmap;

        bitmap = Bitmap.createBitmap(1920/4,1080/4, Bitmap.Config.ARGB_8888);
        try {
            bitmap = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mRgba, bitmap);

            mBitmap.setImageBitmap(bitmap);
            mBitmap.invalidate();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
