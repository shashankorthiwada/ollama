package com.learnai.ollama;

import java.util.List;

public class OllamaResponse {

    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static class Message {

        private String role;
        private String content;
        private List<ToolCall> tool_calls;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ToolCall> getTool_calls() {
            return tool_calls;
        }

        public void setTool_calls(List<ToolCall> tool_calls) {
            this.tool_calls = tool_calls;
        }
    }
}
