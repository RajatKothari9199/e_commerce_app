// JwtResponse.java
package org.example.model;

public class JwtResponse {

    private String token;

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter and setter method
    public String getToken() {
        return token;
    }



    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtResponse that = (JwtResponse) o;
        return token != null ? token.equals(that.token) : that.token == null;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
