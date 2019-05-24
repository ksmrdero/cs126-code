# Face Detection using OpenFrameWorks

My plan for my final project is to implement a face detection program using OpenFrameWorks and OpenCV. The program will connect with a user's camera and will try to detect a face if it is in the view of the camera. This project can be extended to determine the emotion of the current user's face, or also adding filters to the a detected person's face. 

## Resources
This project will be written in C++ and will use the OpenFrameWorks toolkit and the OpenCV library. Functionality such as accessing a users camera can be achieved using the VideoGrabber function in OpenFrameWorks, while a plethora of resources for image processing and machine learning can be found in OpenCV. Specifically, OpenCV contains a built-in function for face detections using Haar Cascades which can be implemented in this project. These resources can be found at:

VideoGrabber: <https://openframeworks.cc/documentation/types/ofBaseVideoGrabber/>  
Haar Cascades: <https://docs.opencv.org/3.4.3/d7/d8b/tutorial_py_face_detection.html>

## Extensions
Once face detection is successful, possible extensions of this project include determining the current emotion of the face. For example, the program can determine if the user is happy if they're smiling, or sad of they're frowning. Furthermore, other extensions could possibly add filters to the face, similar to SnapChat. However, these extensions would only be completed once everything else has been completed and checked.

## Backups
In the case that this project proves to be too difficult, a similar goal can be achieved with a still image and various image processing applications using OpenCV. An interface could allow different options to edit the image, such as finding all the edges with Canny Edge Detection, or doing some geometric transfermations of an image.