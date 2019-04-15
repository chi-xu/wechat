package com.wechat.face;

public class Faces {

    private Landmark landmark;
    private Attributes attributes;
    private Face_rectangle face_rectangle;
    private String face_token;

    public Landmark getLandmark() {
        return landmark;
    }

    public void setLandmark(Landmark landmark) {
        this.landmark = landmark;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Face_rectangle getFace_rectangle() {
        return face_rectangle;
    }

    public void setFace_rectangle(Face_rectangle face_rectangle) {
        this.face_rectangle = face_rectangle;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }
}
