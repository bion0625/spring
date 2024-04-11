package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        LineCallback<Integer> multiplyCallback = new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, multiplyCallback, 1);
    }

    public String concatenate(String filepath) throws IOException {
        LineCallback<String> concatenateCallback = new LineCallback<String>() {
            @Override
            public String doSomethingWithLine(String line, String value) {
                return value + line;
            }
        };
        return lineReadTemplate(filepath, concatenateCallback, "");
    }

    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath)); // 한 줄씩 읽기 편하게 BufferedReader로 파일을 가져온다.
            T res = initVal;
            String line = null;
            while ((line = br.readLine())!=null) { // 파일의 각 라인을 루프를 돌면서 가져오는 것도 템플릿이 담당한다.
                res = callback.doSomethingWithLine(line, res); // 각 라인의 내용을 가지고 계산하는 작업만 콜백에게 맡긴다.
            }
            return res;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) { // BufferedReader 오브젝트가 생성되기 전에 예외가 발생할 수도 있으므로 반드시 null 체크를 먼저 해야 한다.
                try {br.close();} 
                catch (IOException e) {System.out.println(e.getMessage());}
            }
        }
    }

    public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath)); // 한 줄씩 읽기 편하게 BufferedReader로 파일을 가져온다.
            int let = callback.doSomethingWithReader(br); // 콜백 오브젝트 호출. 템플릿에서 만든 컨텍스트 정보인 BufferedReader를 전달해주고 콜백의 작업 결과를 받아둔다.
            return let;
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) { // BufferedReader 오브젝트가 생성되기 전에 예외가 발생할 수도 있으므로 반드시 null 체크를 먼저 해야 한다.
                try {br.close();} 
                catch (IOException e) {System.out.println(e.getMessage());}
            }
        }
    }
}
