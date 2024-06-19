//package springbook.learningtest.spring.web.atmvc;
//
//import java.beans.PropertyEditorSupport;
//
//public class FakeCodePropertyEditor extends PropertyEditorSupport {
//    @Override
//    public String getAsText() {
//        return getCodeString(String.valueOf(getValue()));
//    }
//
//    @Override
//    public void setAsText(String text) throws IllegalArgumentException {
//        setValue(getCodeString(text));
//    }
//
//    private String getCodeString(String codeValue) {
//        switch (codeValue) {
//            case "1": return Code.ADMIN.name();
//            case "2": return Code.USER.name();
//            case "3": return Code.GUEST.name();
//        }
//        return null;
//    }
//}
