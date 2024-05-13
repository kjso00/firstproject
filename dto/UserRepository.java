package com.ohgiraffers.library.dto;

import java.util.ArrayList;

public class UserRepository {

    // UserDTO 객체를 저장하고 출력하는 역할을 합니다.

    private ArrayList<UserDTO> data = new ArrayList<>();

    public void addUser(UserDTO user) { //주어진 UserDTO 객체를 리스트에 추가합니다.
        data.add(user);
    }

    public void print() {
        for (Object user : data) { // 리스트에 저장되어있는 모든 사용자 정보를 차례대로 출력합니다.
            System.out.print(user);
        }

    }

    public boolean login(String userName, String phone) {
        for (UserDTO user : data) {
            if (user.getName().equals(userName) && user.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeUser(String userName, String phone) {
        for (UserDTO user : data) {
            if (user.getName().equals(userName) && user.getPhone().equals(phone)) {

                data.remove(user);
                return true;

            }
        }
        return false;
    }



}
