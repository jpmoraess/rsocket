package br.com.moraesit.rsocket.dto;

public class ResponseDTO {
    private int input;
    private int output;

    public ResponseDTO() {

    }

    public ResponseDTO(int input, int output) {
        this.input = input;
        this.output = output;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "input=" + input +
                ", output=" + output +
                '}';
    }
}
