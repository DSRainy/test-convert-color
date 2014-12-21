/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seniorproject.augmentedreality.test;

import java.util.ArrayList;
import org.bytedeco.javacpp.Loader;
import static org.bytedeco.javacpp.helper.opencv_imgproc.cvFindContours;
import org.bytedeco.javacpp.opencv_core.CvBox2D;
import org.bytedeco.javacpp.opencv_core.CvContour;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.CvSize2D32f;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvInRangeS;
import static org.bytedeco.javacpp.opencv_core.cvScalar;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2HSV;
import static org.bytedeco.javacpp.opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;
import static org.bytedeco.javacpp.opencv_imgproc.CV_MOP_OPEN;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RETR_LIST;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvMinAreaRect2;
import static org.bytedeco.javacpp.opencv_imgproc.cvMorphologyEx;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;

/**
 *
 * @author RainWhileLoop
 */
public class TestJavaCV {

    private static final int IMG_SCALE = 2;
    private int hueLower, hueUpper, satLower, satUpper, briLower, briUpper;
    private IplImage scaleImg;
    private IplImage hsvImg;
    private IplImage imgThreshed;
    private Point cogPt;
    private int contourAxisAngle;
    private ArrayList<Point> fingerTips;

    public static void main(String[] args) {

    }

    public void update(IplImage im) {
        cvResize(im, scaleImg);
        cvCvtColor(scaleImg, hsvImg, CV_BGR2HSV);
        cvInRangeS(
                hsvImg,
                cvScalar(hueLower, satLower, briLower, 0),
                cvScalar(hueUpper, satUpper, briUpper, 0), imgThreshed);
        cvMorphologyEx(imgThreshed, imgThreshed, null, null, CV_MOP_OPEN, 1);
        CvSeq bigContour = findBiggestContour(imgThreshed);
        if (bigContour == null) {
            return;
        }
//        extractContourInfo(bigContour, IMG_SCALE);
//        findFingerTips(bigContour, IMG_SCALE);
//        nameFingers(cogPt, contourAxisAngle, fingerTips);
    }

    private static final float SMALLEST_AREA = 600.0f;
    private CvMemStorage contourStorage;

    private CvSeq findBiggestContour(IplImage imgThreshed) {
        CvSeq bigContour = null;

        CvSeq contours = new CvSeq(null);
        cvFindContours(imgThreshed, contourStorage, contours, Loader.sizeof(CvContour.class), CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
        float maxArea = SMALLEST_AREA;
        CvBox2D maxBox = null;
        while (contours != null && !contours.isNull()) {
            if (contours.elem_size() > 0) {
                CvBox2D box = cvMinAreaRect2(contours, contourStorage);
                if (box != null) {
                    CvSize2D32f size = box.size();
                    float area = size.width() * size.height();
                    if (area > maxArea) {
                        maxArea = area;
                        bigContour = contours;
                    }
                }
            }
            contours = contours.h_next();
        }
        return bigContour;
    }
}
