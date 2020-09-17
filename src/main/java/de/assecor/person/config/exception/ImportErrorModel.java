package de.assecor.person.config.exception;

class ImportErrorModel {

    private final String[] line;

    private final Long lineNumber;

    private final String errorMessage;

    public ImportErrorModel(String[] line, Long lineNumber, String errorMessage) {
        this.line = line;
        this.lineNumber = lineNumber;
        this.errorMessage = errorMessage;
    }

    public String[] getLine() {
        return line;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String toString() {
        return "Import error on line " +
                lineNumber + ": " +
                errorMessage;
    }
}
