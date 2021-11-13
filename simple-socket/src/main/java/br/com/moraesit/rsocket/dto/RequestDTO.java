package br.com.moraesit.rsocket.dto;

public class RequestDTO {
    private int input;

    public RequestDTO() {
    }

    public RequestDTO(int input) {
        this.input = input;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "input=" + input +
                '}';
    }
}
