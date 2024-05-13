package com.ohgiraffers.library.view;

import com.ohgiraffers.library.dto.LibraryDTO;
import com.ohgiraffers.library.dto.UserDTO;
import com.ohgiraffers.library.dto.UserRepository;
import com.ohgiraffers.library.service.LibraryService;

import java.sql.SQLException;
import java.util.Scanner;

public class LibraryView {

    private static boolean state = true;
    private static LibraryService libraryService;

    public static void run() {
        try {
            libraryService = new LibraryService();
        } catch (Exception e) {
            System.out.println("LibraryService 생성 중 오류가 발생했습니다: " + e.getMessage());
            return; // 프로그램 종료
        }
        UserRepository userRepository = new UserRepository();
        while (state) {
            try {
                Thread.sleep(2000); // Thread.sleep(2000) 메서드를 호출하여 현재 스레드를 2초 동안 일시 중지합니다.
            } catch (InterruptedException e) {
                System.out.println("오류가 발생 했습니다.");
                e.printStackTrace();
            }
            System.out.println("\n무인 관리대에 오신걸 환영 합니다.\n");
            System.out.println("화면 번호를 입력해주세요 : ");
            System.out.println("1. 도서 검색 "); // [재희] - 도서이름으로 검색
            System.out.println("2. 회원 등록 ");  // [효연, 주연]
            System.out.println("3. 회원 로그인 "); // [주연]
            //  로그인 했을경우 대여, 반납 가능 →
//                                             System.out.println("1. 도서 대여 "); // [재희]
//                                             System.out.println("2. 도서 반납 "); // [재희]
            System.out.println("4. 관리자모드 ");
            //  로그인 했을경우 대여, 반납 가능 →
//                                            System.out.println("1. 전체 회원 조회 "); // [기호] - 전체 회원 조회
//                                            System.out.println("2. 특정 회원 삭제 "); // [준성]

            System.out.print("\n원하는 항목을 선택해 주세요. : ");


            Scanner sc = new Scanner(System.in);
            int index = Integer.parseInt(sc.nextLine());

            switch (index) {
                case 1:
                    libraryFindByTitle(); // 도서 이름으로 검색
                    break;

                case 2: // 회원등록

                    System.out.println("\n회원 등록을 진행합니다.");
                    System.out.print("이름을 입력해주세요. ex)이상우 : ");
                    String name = sc.nextLine();
                    System.out.print("나이를 입력해주세요. ex)20 : ");
                    int age = sc.nextInt();
                    sc.nextLine(); // 버퍼를 비워줍니다.
                    System.out.print("휴대폰 번호를 입력해주세요. : ");
                    String phone = sc.nextLine();

                    UserDTO userDTO = new UserDTO(name,age,phone); //생성자를 이용하여 안정성과 가독성을 높임
                    userRepository.addUser(userDTO);
                    System.out.println(userDTO+"님 등록이 완료되었습니다.\n");

                    break; //


                case 3: // 회원 로그인

                    System.out.println("로그인을 시작합니다.\n");

                    System.out.print("사용자명 :");
                    String userName = sc.nextLine();
                    System.out.print("phone : ");
                    String userPhone = sc.nextLine();

                    if (userRepository.login(userName, userPhone)) {
                        System.out.println("\n로그인 성공하였습니다.");
                    } else {
                        System.out.println("\n로그인에 실패하였습니다. 사용자명 or 비밀번호를 다시 확인해주세요.\n");
                    }
                    break;


                case 4: // 전체 회원조회 - 관리자만 접근 가능

                    String password = "1004";

                    System.out.print("\n허용된 관리자만 접근이 가능합니다. 암호를 입력해 주세요 : ");
                    String inputPassword = sc.next(); // 비밀번호 입력 받음

                    if (inputPassword.equals(password)) { // 입력된 비밀번호가 설정된 비밀번호와 일치하는지 확인
                        // 비밀번호가 일치하면 값을 보여줌
                        System.out.println("\n1. 회원 조회");
                        System.out.println("2. 회원 삭제");
                        System.out.print("\n원하는 항목을 선택해 주세요. : ");

                        int choiceAdm = sc.nextInt();
                        switch (choiceAdm) {
                            case 1:
                                System.out.println("\n전체 회원을 조회합니다. ");
                                userRepository.print();
                                System.out.println("\n");
                                break;

                            case 2: // 회원 삭제

                                System.out.println("특정 회원을 삭제합니다.");
                                System.out.println("삭제할 회원의 이름 : ");
                                String deleteName = sc.nextLine();
                                sc.nextLine();
                                System.out.println("삭제할 회원의 전화번호 : ");
                                String deletePhone = sc.nextLine();

                                if (userRepository.removeUser(deleteName,deletePhone)) {
                                    System.out.println(deleteName + "님의 정보가 삭제되었습니다.");
                                } else {
                                    System.out.println("삭제할 회원을 찾을 수 없습니다.");
                                }
                                break;

                            default:
                                System.out.println("\n해당하는 관리자 항목이 없습니다.\n");
                        }

                    } else {
                        // 비밀번호가 일치하지 않으면 오류 메시지 출력
                        System.out.println("죄송합니다. 허용된 관리자가 아닙니다.\n");
                    }

                    break;



                default:
                    System.out.println("잘못된 입력입니다.");
                    break;

            }

            System.out.print("종료를 하시겠습니까? (yes Or no) : ");
            String result = sc.nextLine();

            if (result.equals("yes")) {
                state = false;
                sc.close();
            }
        }
    }

    public static void libraryFindByTitle() {
        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 책의 이름을 입력하세요 : ");
        String name = sc.nextLine();
        LibraryDTO book = null;

        try {
            book = libraryService.libraryFindByBook(name);
            if (book != null) {
                System.out.println(book);
            } else {
                System.out.println("해당 책이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("책 이름 조회 중 오류가 발생했습니다: " );

        }
    }
}