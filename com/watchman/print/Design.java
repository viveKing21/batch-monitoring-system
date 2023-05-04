package com.watchman.print;

public class Design{
    public static final String PREFIX = "\u001B[";
    public static final String POSTFIX = "m";
    public static final byte BOLD = 1;
    public static final byte ITALIC = 3;
    public static final byte UNDERLINE = 4;
    public static final byte STRIKE = 9;
    public static final byte DOUBLE_UNDERLINE = 21;
    public static final byte BLACK = 30;
    public static final byte RED = 31;
    public static final byte GREEN = 32;
    public static final byte YELLOW = 33;
    public static final byte BLUE = 34;
    public static final byte PURPLE = 35;
    public static final byte CYAN = 36;
    public static final byte WHITE = 37;
    public static final byte GRAY = 38;
    public static final byte BLACK_BACKGROUND = 40;
    public static final byte RED_BACKGROUND = 41;
    public static final byte GREEN_BACKGROUND = 42;
    public static final byte YELLOW_BACKGROUND = 43;
    public static final byte BLUE_BACKGROUND = 44;
    public static final byte PURPLE_BACKGROUND = 45;
    public static final byte CYAN_BACKGROUND = 46;
    public static final byte WHITE_BACKGROUND = 47;
    public static final String RESET = "\033[0m"; 

    private int screen;
    private int lineHeight = 1;
    private int letterSpacing = 0;
    private byte borderColor = 0;
    private byte backgroundColor = 0;
    private byte textColor = 0;
    private int[] margin = new int[4];
    private int[] padding = new int[4];
    private int[] border = new int[4];
    private String[] borderStyle = new String[2];
    
    public byte bold = 0;
    public byte italic = 0;
    public byte underline = 0;
    public byte strike = 0;
    public byte doubleLine = 0;
    

    public Design(int screen){
        this.screen = screen;
    }
    public int getScreen() {
        return screen;
    }
    public void setScreen(int screen) {
        this.screen = Math.max(screen, 0);
    }
    public int getLineHeight() {
        return lineHeight;
    }
    public void setLineHeight(int lineHeight) {
        this.lineHeight = Math.max(lineHeight, 1);
    }
    public int getLetterSpacing() {
        return letterSpacing;
    }
    public void setLetterSpacing(int letterSpacing) {
        this.letterSpacing = Math.max(letterSpacing, 0);
    }
    public byte getBorderColor() {
        return this.borderColor;
    }
    public void setBorderColor(byte borderColor) {
        this.borderColor = borderColor;
    }
    public byte getBackgroundColor() {
        return this.backgroundColor;
    }
    public void setBackgroundColor(byte backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public byte getTextColor() {
        return this.textColor;
    }
    public void setTextColor(byte textColor) {
        this.textColor = textColor;
    }
    public void bold(boolean isBold) {
        this.bold = isBold ? BOLD:0;
    }
    public void italic(boolean isItalic) {
        this.italic = isItalic ? ITALIC:0;
    }
    public void underline(boolean isUnderline) {
        this.underline = isUnderline ? UNDERLINE:0;
    }
    public void strike(boolean isStrike) {
        this.strike = isStrike ? STRIKE:0;
    }
    public void doubleLine(boolean isDoubleLine) {
        this.doubleLine = isDoubleLine ? DOUBLE_UNDERLINE:0;
    }
    public void setMargin(int margin) {
        this.margin[0] = margin;
        this.margin[1] = margin;
        this.margin[2] = margin;
        this.margin[3] = margin;
    }
    public void setMargin(int top, int right, int bottom, int left) {
        this.margin[0] = top;
        this.margin[1] = right;
        this.margin[2] = bottom;
        this.margin[3] = left;
    }
    public void setPadding(int padding) {
        this.padding[0] = padding;
        this.padding[1] = padding;
        this.padding[2] = padding;
        this.padding[3] = padding;
    }
    public void setPadding(int top, int right, int bottom, int left) {
        this.padding[0] = top;
        this.padding[1] = right;
        this.padding[2] = bottom;
        this.padding[3] = left;
    }
    public void setBorder(int border) {
        this.border[0] = border;
        this.border[1] = border;
        this.border[2] = border;
        this.border[3] = border;
    }
    public void setBorder(int top, int right, int bottom, int left) {
        this.border[0] = top;
        this.border[1] = right;
        this.border[2] = bottom;
        this.border[3] = left;
    }
    public void setBorderStyle(String borderStyle) {
        this.borderStyle[0] = borderStyle;
        this.borderStyle[1] = borderStyle;
    }
    public void setBorderStyle(String vertical, String horizontal) {
        this.borderStyle[0] = vertical;
        this.borderStyle[1] = horizontal;
    }
    public int getMarginTop(){
        return margin[0];
    }
    public int getMarginRight(){
        return margin[1];
    }
    public int getMarginBottom(){
        return margin[2];
    }
    public int getMarginLeft(){
        return margin[3];
    }
    public int getPaddingTop(){
        return padding[0];
    }
    public int getPaddingRight(){
        return padding[1];
    }
    public int getPaddingBottom(){
        return padding[2];
    }
    public int getPaddingLeft(){
        return padding[3];
    }
    public int getBorderTop(){
        return border[0];
    }
    public int getBorderRight(){
        return border[1];
    }
    public int getBorderBottom(){
        return border[2];
    }
    public int getBorderLeft(){
        return border[3];
    }
    public String getBorderStyleVertical(){
        return borderStyle[0];
    }
    public String getBorderStyleHorizontal(){
        return borderStyle[1];
    }
}