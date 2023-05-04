package com.watchman.print;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Print{
    public static String createTextNode(String txt, int length){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < length; i++) str.append(txt);
        return str.toString();
    }
    public static String wrapStyle(Object txt, String code){
        if(code == null) return txt.toString();
        StringBuilder wrapString = new StringBuilder(code);
        wrapString.append(txt.toString());
        wrapString.append(Design.RESET);

        return wrapString.toString();
    }
    public static String wrapStyle(Object txt, byte... STYLE){
        if(STYLE.length == 0) return txt.toString();
        
        StringBuilder wrapString = new StringBuilder(Design.PREFIX);

        boolean hasValidStyle = false;

        for(int i = 0; i < STYLE.length; i++){
            if(STYLE[i] == 0) continue;
            if(i > 0) wrapString.append(";");
            wrapString.append(STYLE[i]);
            hasValidStyle = true;
        }
        
        if(hasValidStyle == false) return txt.toString();

        wrapString.append(Design.POSTFIX);
        wrapString.append(txt.toString());
        wrapString.append(Design.RESET);

        return wrapString.toString();
    }
    public static String parseDesign(String ansiString){
        String regex = "\\u001B\\[[;\\d]*[ -/]*[@-~]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ansiString);
        if (matcher.find()) {
            String escapeCode = matcher.group();
            return escapeCode;
        }
        return null;
    }
    public static String parseString(String ansiString){
        String regex = "\u001B\\[[;\\d]*[ -/]*[@-~]";
        return ansiString.replaceAll(regex, "");
    }
    public static String wrapDesign(Object txt, Design design){
        String text = txt.toString();
        String parsedTxt = parseString(text);

        final String WHITE_SPACE = wrapStyle(" ", design.getBackgroundColor());
        final String NEXT_LINE = createTextNode("\n", design.getLineHeight());

        StringBuilder designedString = new StringBuilder();

        short minWidth = 0;
        minWidth += design.getMarginLeft() + design.getMarginRight();
        minWidth += design.getPaddingLeft() + design.getPaddingRight();
        minWidth += design.getBorderLeft() + design.getBorderRight();

        short minHeight = 0;
        minHeight += design.getMarginTop() + design.getMarginBottom();
        minHeight += design.getPaddingTop() + design.getPaddingBottom();
        minHeight += design.getBorderTop() + design.getBorderBottom();

        short width = (short)Math.max(minWidth + 1, design.getScreen());
        
        short contentWidth = (short)Math.max(width - minWidth, 1);
        short contentHeight = 0;
        
        short index = 1;

        for(short i = 0; i < parsedTxt.length(); i++){
            if(parsedTxt.charAt(i) == '\n' || index == contentWidth){
                contentHeight++;
                index = 1;
            }
            index++;
        }

        contentHeight = (short)Math.max(contentHeight, 1);

        short height = (short)(minHeight + contentHeight); 

        short startLeftMargin = 0;
        short endLeftMargin = (short)design.getMarginLeft();

        short startLeftBorder = endLeftMargin;
        short endLeftBorder = (short)(startLeftBorder + design.getBorderLeft());

        short startLeftPadding = endLeftBorder;
        short endLeftPadding = (short)(startLeftPadding + design.getPaddingLeft());

        short startRightPadding = (short)(contentWidth + endLeftPadding);
        short endRightPadding = (short)(startRightPadding + design.getPaddingRight());

        short startRightBorder = endRightPadding;
        short endRightBorder = (short)(startRightBorder + design.getBorderRight());

        short startRightMargin = endRightBorder;
        short endRightMargin = (short)(startRightMargin + design.getMarginRight());

        short startTopMargin = 0;
        short endTopMargin = (short)design.getMarginTop();

        short startTopBorder = endTopMargin;
        short endTopBorder = (short)(startTopBorder + design.getBorderTop());

        short startTopPadding = endTopBorder;
        short endTopPadding = (short)(startTopPadding + design.getPaddingTop());

        short startBottomPadding = (short)(contentHeight + endTopPadding);
        short endBottomPadding = (short)(startBottomPadding + design.getPaddingBottom());

        short startBottomBorder = endBottomPadding;
        short endBottomBorder = (short)(startBottomBorder + design.getBorderBottom());

        short startBottomMargin = endBottomBorder;
        short endBottomMargin = (short)(startBottomMargin + design.getMarginBottom());

        int charIndex = 0;
        int space = 0;
        
        Stack<StringBuilder> customStyles = new Stack<>();

        for(int line = 1; line <= height; line++){
            boolean nextLine = false;

            if((line > startTopMargin && line <= endTopMargin) || 
               (line > startBottomMargin && line <= endBottomMargin)){
                designedString.append("\n");
                continue;
            }

            int leftBorderIndex = 0;
            int rightBorderIndex = 0;
            int topBorderIndex = 0;
            int bottomBorderIndex = 0;

            for(int cursor = 1; cursor <= width; cursor++){
                if((cursor > startLeftMargin && cursor <= endLeftMargin) ||
                   (cursor > startRightMargin && cursor <= endRightMargin)){
                    designedString.append(" ");
                }
                else if(cursor > startLeftBorder && cursor <= endLeftBorder){
                    designedString.append(wrapStyle(design.getBorderStyleVertical().charAt(leftBorderIndex),design.getBorderColor()));
                    leftBorderIndex = (leftBorderIndex + 1) % design.getBorderStyleVertical().length();
                }
                else if(cursor > startRightBorder && cursor <= endRightBorder){
                    designedString.append(wrapStyle(design.getBorderStyleVertical().charAt(rightBorderIndex),design.getBorderColor()));
                    rightBorderIndex = (rightBorderIndex + 1) % design.getBorderStyleVertical().length();
                }
                else if(line > startTopBorder && line <= endTopBorder){
                    designedString.append(wrapStyle(design.getBorderStyleHorizontal().charAt(topBorderIndex),design.getBorderColor()));
                    topBorderIndex = (topBorderIndex + 1) % design.getBorderStyleHorizontal().length();
                }
                else if(line > startBottomBorder && line <= endBottomBorder){
                    designedString.append(wrapStyle(design.getBorderStyleHorizontal().charAt(bottomBorderIndex),design.getBorderColor()));
                    bottomBorderIndex = (bottomBorderIndex + 1) % design.getBorderStyleHorizontal().length();
                }
                else if((line > startTopPadding && line <= endTopPadding) ||
                        (line > startBottomPadding && line <= endBottomPadding) ||
                        (cursor > startLeftPadding && cursor <= endLeftPadding) ||
                        (cursor > startRightPadding && cursor <= endRightPadding)){
                    designedString.append(WHITE_SPACE);
                }
                else{
                    if(charIndex >= text.length()){
                        designedString.append(WHITE_SPACE);
                    }
                    else{
                        // next line detection
                        if(text.charAt(charIndex) == '\n' || text.charAt(charIndex) == '\r'){
                            nextLine = true;
                            space = 0;
                            charIndex++;
                        }
                        if(nextLine){
                            designedString.append(WHITE_SPACE);
                            continue;
                        }
                        
                        // unicode detection
                        if(text.charAt(charIndex) == Design.PREFIX.charAt(0)){
                            if(customStyles.isEmpty()){
                                customStyles.push(new StringBuilder());
                            }
                            while(charIndex < text.length()){
                                customStyles.peek().append(text.charAt(charIndex));
                                charIndex++;
                                if(text.charAt(charIndex-1) == 'm') break;
                            }
                            cursor--;
                            continue;
                        }
                        if(text.charAt(charIndex) == Design.RESET.charAt(0)){
                            customStyles.pop();
                            while(charIndex < text.length()){
                                charIndex++;
                                if(text.charAt(charIndex-1) == 'm') break;
                            }
                            cursor--;
                            continue;
                        }
                        // if(text.charAt(charIndex) == '\u001B' || text.charAt(charIndex) == '\033'){
                        //     while(charIndex < text.length()){
                        //         if(text.charAt(charIndex) == '\u001B') startStyle.append(text.charAt(charIndex));
                        //         charIndex++;
                        //         if(text.charAt(charIndex-1) == 'm') break;
                        //     }
                        //     cursor--;
                        //     continue;
                        // }

                        if(space > 0){
                            designedString.append(WHITE_SPACE);
                            space--;
                        }
                        else{
                            if(!customStyles.isEmpty()) {
                                designedString.append(wrapStyle(text.charAt(charIndex), customStyles.peek().toString()));
                            }
                            else
                                designedString.append(wrapStyle(text.charAt(charIndex), 
                                    design.getTextColor(),
                                    design.getBackgroundColor(),
                                    design.bold,
                                    design.doubleLine,
                                    design.italic,
                                    design.strike,
                                    design.underline
                                ));
                            space = design.getLetterSpacing();
                            charIndex++;
                        }
                    }

                }
            }
            designedString.append(NEXT_LINE);
        }

        return designedString.toString();
    }
    public static void printStyle(Object txt, byte... STYLES){
        System.out.println(wrapStyle(txt, STYLES));
    }
    public static void printDesign(Object txt, Design design){
        System.out.println(wrapDesign(txt, design));
    }
}
