package semiPJ;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {
	@FXML private ImageView imageView;
	@FXML private TableView<Member> tableView;
	@FXML private TextField code, name, id, birth, phone, times;
	@FXML private Button memberAdd, timeAdd, memberDelete, close;
	@FXML private RadioButton seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10, seat11, seat12, seat13;
	@FXML private RadioButton seat14, seat15, seat16, seat17, seat18, seat19, seat20, seat21, seat22, seat23, seat24, seat25;

	ObservableList<Member> memberslist = null; // tableView 목록 저장

	/* tableView 목록을 저장하는 변수(전역변수 선언) */
	List<Member> list = null; // table 레코드 저장

	/* MySQL 연동을 위한 변수 */
	Connection con = null;// 1. MySQL DB 연결 변수
	PreparedStatement pstmt = null;// 2. 쿼리문 실행 변수
	ResultSet rs = null; // 3. 쿼리 실행 결과 변수

	@Override
	public void initialize(URL local, ResourceBundle resource) {
		// TableView의 컬럼에 Model 속성 지정(code name, time)
		TableColumn tcgCode = tableView.getColumns().get(0);
		tcgCode.setCellValueFactory(new PropertyValueFactory("code"));
		tcgCode.setStyle("-fx-alignment: CENTER;"); // css 적용

		TableColumn tcgName = tableView.getColumns().get(1);
		tcgName.setCellValueFactory(new PropertyValueFactory("name"));
		tcgName.setStyle("-fx-alignment: CENTER;"); // css 적용

		TableColumn tcgId = tableView.getColumns().get(2);
		tcgId.setCellValueFactory(new PropertyValueFactory("id"));
		tcgId.setStyle("-fx-alignment: CENTER;"); // css 적용

		TableColumn tcgTimes = tableView.getColumns().get(3);
		tcgTimes.setCellValueFactory(new PropertyValueFactory("times"));
		tcgTimes.setStyle("-fx-alignment: CENTER;"); // css 적용

		// MySQL 변수 초기화
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/work";
		String uid = "scott";
		String upwd = "tiger";

		try {
			// (1) 드라이버 로딩
			Class.forName(driver);
			// (2) 데이터베이스 연결
			con = DriverManager.getConnection(url, uid, upwd);
			// (3) 쿼리문 작성
			String sql = "select * from member order by code";
			// (4) 쿼리문 실행
			pstmt = con.prepareStatement(sql); // 쿼리문 실행
			// (5) select문 실행 -> 검색 결과 저장(rs)
			rs = pstmt.executeQuery(); // select문 실행

			int code;
			double times;
			String name, id, birth, phone;
			// (6) 레코드 출력 및 list 원소 추가
			list = new ArrayList<Member>();
			while (rs.next()) { // 레코드 수 만큼 반복
				code = rs.getInt("code");
				name = rs.getString("name");
				id = rs.getString("id");
				birth = rs.getString("birth");
				phone = rs.getString("phone");
				times = rs.getDouble("times");
				Member m = new Member(code, name, id, birth, phone, times);
				list.add(m);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("JDBC DRIVER 로딩 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ObservableList에 list 원소 추가
		memberslist = FXCollections.observableArrayList();
		for (Member mlist : list) {
			memberslist.add(mlist);
		}
		// ObservableList 원소를 TableView 목록으로 추가
		tableView.setItems(memberslist); // 인수 : ObservableList 참조변수

		// 회원 클릭시 왼쪽에 정보 출력
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Member>() {
			@Override
			public void changed(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {

				Member members = tableView.getSelectionModel().getSelectedItem();
				code.setText(Integer.toString(members.getCode()));
				name.setText(members.getName());
				id.setText(members.getId());
				birth.setText(members.getBirth());
				phone.setText(members.getPhone());
				times.setText(Double.toString(members.getTimes()));

			}
		});

		// RadioButton에서 자리있는 곳은 선택되어 있도록
		RadioButton[] rb = { seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10, seat11, seat12,
				seat13, seat14, seat15, seat16, seat17, seat18, seat19, seat20, seat21, seat22, seat23, seat24,
				seat25 };

		try {
			// (1) 드라이버 로딩
			Class.forName(driver);
			// (2) 데이터베이스 연결
			con = DriverManager.getConnection(url, uid, upwd);
			String sql = "select * from member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			int code;

			while (rs.next()) {
				code = rs.getInt("code");
				for (int i = 0; i < 25; i++) {
					if (i < 10) {
						int x = Integer.parseInt(rb[i].getText().substring(0, 1));
						if (x == code) {
							rb[i].setSelected(true);
						}
					} else if (i >= 10) {
						int y = Integer.parseInt(rb[i].getText().substring(0, 2));
						if (y == code) {
							rb[i].setSelected(true);
						}
					}
				}
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 닫기 버튼 -> 현재 창 닫기
		close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
			}
		});

		// 회원 삭제 버튼
		memberDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				int seat_num = Integer.parseInt(code.getText()); // 삭제할 자리 값 받기
				int lseat_num = tableView.getSelectionModel().getSelectedIndex();

				try {
					// 드라이버 로딩
					Class.forName(driver);
					con = DriverManager.getConnection(url, uid, upwd);

					// 선택된 회원 radio버튼에서 삭제
					String sql = "select * from member where code = ?";
					pstmt = con.prepareStatement(sql); // 쿼리문 실행
					pstmt.setInt(1, seat_num);
					rs = pstmt.executeQuery();

					int code;
					while (rs.next()) {
						code = rs.getInt("code");
						for (int i = 0; i < 25; i++) {
							if (i < 10) {
								int x = Integer.parseInt(rb[i].getText().substring(0, 1));
								if (x == code) {
									rb[i].setSelected(false);
								}
							} else if (i >= 10) {
								int y = Integer.parseInt(rb[i].getText().substring(0, 2));
								if (y == code) {
									rb[i].setSelected(false);
								}
							}
						}
					}

					sql = "delete from member where code = ?";
					pstmt = con.prepareStatement(sql); // 쿼리문 실행
					pstmt.setInt(1, seat_num);

					int result = pstmt.executeUpdate();

					if (result >= 1) {
						JOptionPane.showMessageDialog(null, "회원의 PC를 종료하였습니다..");
						list.remove(lseat_num);
						memberslist.remove(lseat_num);
					} else {
						JOptionPane.showMessageDialog(null, "회원의 PC가 정상적으로 종료되지 않았습니다..");
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					try {
						rs.close();
						pstmt.close();
						con.close();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}

			}

		});

		// 회원 추가 버튼
		memberAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// memberAdd창을 나타낼 stage
				Stage stage = new Stage(StageStyle.UTILITY);
				// 컨트롤러에서 레이아웃 파일(memberFxml.fxml) 사용 : Parent 객체 가져오기
				Parent parent = null;
				try {
					parent = FXMLLoader.load(getClass().getResource("memberFxml.fxml"));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				// Scene 객체 생성
				Scene scene = new Scene(parent);

				// Stage에 Scene 올리기
				stage.setScene(scene);
				stage.setTitle("회원 추가");
				stage.show();

				// 객체 가져오기
				TextField code = (TextField) parent.lookup("#code");
				TextField name = (TextField) parent.lookup("#name");
				TextField id = (TextField) parent.lookup("#id");
				TextField birth = (TextField) parent.lookup("#birth");
				TextField phone = (TextField) parent.lookup("#phone");
				TextField times = (TextField) parent.lookup("#times");
				Button add = (Button) parent.lookup("#add");
				Button delete = (Button) parent.lookup("#delete");

				// 추가버튼 클릭시
				add.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						int xcode = Integer.parseInt(code.getText());
						String xname = name.getText();
						String xid = id.getText();
						String xbirth = birth.getText();
						String xphone = phone.getText();
						double xtimes = Double.parseDouble((times.getText()));

						try {
							Class.forName(driver);
							con = DriverManager.getConnection(url, uid, upwd);
							String sql = "insert into member values(?, ? ,?, ?, ?, ?)";
							// (4) 쿼리문 실행
							pstmt = con.prepareStatement(sql); // 쿼리문 실행
							pstmt.setInt(1, xcode);
							pstmt.setString(2, xname);
							pstmt.setString(3, xid);
							pstmt.setString(4, xbirth);
							pstmt.setString(5, xphone);
							pstmt.setDouble(6, xtimes);
							// (5) executeUpdate
							int result = pstmt.executeUpdate();

							sql = "select * from member order by code";
							pstmt = con.prepareStatement(sql);
							rs = pstmt.executeQuery();

							// 레코드 출력 및 list에 원소 추가
							list = new ArrayList<Member>();
							int code;
							while (rs.next()) {
								int lcode = rs.getInt("code");
								String lname = rs.getString("name");
								String lid = rs.getString("id");
								String lbirth = rs.getString("birth");
								String lphone = rs.getString("phone");
								Double ltimes = rs.getDouble("times");

								Member member = new Member(lcode, lname, lid, lbirth, lphone, ltimes);
								list.add(member);

								// 회원추가했을 떄 radio버튼에도 추가
								code = rs.getInt("code");
								for (int i = 0; i < 25; i++) {
									if (i < 10) {
										int x = Integer.parseInt(rb[i].getText().substring(0, 1));
										if (x == code) {
											rb[i].setSelected(true);
										}
									} else if (i >= 10) {
										int y = Integer.parseInt(rb[i].getText().substring(0, 2));
										if (y == code) {
											rb[i].setSelected(true);
										}
									}
								}

							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						} finally {
							try {
								rs.close();
								pstmt.close();
								con.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// Observablelist에 list원소 추가
						memberslist = FXCollections.observableArrayList();
						for (Member mlist : list)
							memberslist.add(mlist);

						// ObservableList 원소를 TableView 목록으로 추가
						tableView.setItems(memberslist);
						stage.close();
					}
				});
				// 취소 버튼 클릭 시
				delete.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						stage.close();
					}
				});
			}

		}); // 회원 추가 end

		// 시간 추가 버튼 클릭 시
		timeAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// timeAdd창을 나타낼 stage
				Stage stage = new Stage(StageStyle.UTILITY);
				// 컨트롤러에서 레이아웃 파일(memberFxml.fxml) 사용 : Parent 객체 가져오기
				Parent parent = null;
				try {
					parent = FXMLLoader.load(getClass().getResource("timeFxml.fxml"));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				// Scene 객체 생성
				Scene scene = new Scene(parent);

				// Stage에 Scene 올리기
				stage.setScene(scene);
				stage.setTitle("시간 추가");
				stage.show();

				// 객체 가져오기
				TextField time_name = (TextField) parent.lookup("#time_name");
				TextField time_id = (TextField) parent.lookup("#time_id");
				ComboBox<?> cbbox = (ComboBox<?>) parent.lookup("#cbbox");
				Button time_add = (Button) parent.lookup("#time_add");
				Button time_exit = (Button) parent.lookup("#time_exit");

				// 추가 버튼 클릭 시
				time_add.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						String tname = time_name.getText();
						String tid = time_id.getText();

						try {
							Class.forName(driver);
							con = DriverManager.getConnection(url, uid, upwd);
							String sql = "select * from member where name = ? and id = ?";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, tname);
							pstmt.setString(2, tid);
							rs = pstmt.executeQuery();

							list = new ArrayList<Member>();
							// 기존 시간에 추가 시간을 더하여 DB update
							while (rs.next()) { // 해당 이름을 가진 회원이 있다면
								Double timess = rs.getDouble("times");
								double total = 0;
								// 시간 비교
								if (cbbox.getSelectionModel().getSelectedItem().equals("1시간")) {
									total = timess + 1.0;
								} else if (cbbox.getSelectionModel().getSelectedItem().equals("2시간")) {
									total = timess + 2.0;
								} else if (cbbox.getSelectionModel().getSelectedItem().equals("3시간")) {
									total = timess + 3.0;
								} else if (cbbox.getSelectionModel().getSelectedItem().equals("5시간")) {
									total = timess + 5.0;
								} else if (cbbox.getSelectionModel().getSelectedItem().equals("9시간")) {
									total = timess + 9.0;
								} else if (cbbox.getSelectionModel().getSelectedItem().equals("11시간")) {
									total = timess + 11.0;
								}
								sql = "update member set times=? where name = ? and id =?";
								pstmt = con.prepareStatement(sql);
								pstmt.setDouble(1, total);
								pstmt.setString(2, tname);
								pstmt.setString(3, tid);

								int result = pstmt.executeUpdate();
								if (result >= 1)
									JOptionPane.showMessageDialog(null, "시간이 추가 되었습니다.");
								else
									JOptionPane.showMessageDialog(null, "회원의 이름과 ID를 다시 확인해 주세요.");

							}
							// tableView 갱신
							sql = "select * from member order by code";
							pstmt = con.prepareStatement(sql);
							rs = pstmt.executeQuery();

							list = new ArrayList<Member>();
							while (rs.next()) {
								int lcode = rs.getInt("code");
								String lname = rs.getString("name");
								String lid = rs.getString("id");
								String lbirth = rs.getString("birth");
								String lphone = rs.getString("phone");
								Double ltimes = rs.getDouble("times");

								Member member = new Member(lcode, lname, lid, lbirth, lphone, ltimes);
								list.add(member);

							}

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						} finally {
							try {
								rs.close();
								pstmt.close();
								con.close();
							} catch (Exception e2) {
								// TODO: handle exception
								e2.printStackTrace();
							}
						}
						// Observablelist에 list원소 추가
						memberslist = FXCollections.observableArrayList();
						for (Member mlist : list)
							memberslist.add(mlist);

						// ObservableList 원소를 TableView 목록으로 추가
						tableView.setItems(memberslist);

					} // handle end

				});
				// 취소 버튼 클릭 시
				time_exit.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						stage.close();
					}
				}); // time_exit end
			}

		}); // 시간추가 end

	}

}
