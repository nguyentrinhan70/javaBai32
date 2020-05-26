package nguyentrinhan70.example.com.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class NhanVienUI extends JFrame {
	JComboBox<String> cboPhongBan;
	Connection conn;
	PreparedStatement preparedStatement;
	
	JList<String> listNhanVien;
	public NhanVienUI(String title){
		super(title);
		addControls();
		addEvents();
		ketNoiDuLieu();
		hienThiDanhSachPhongBan();
	}

	private void hienThiDanhSachPhongBan() {
		// TODO Auto-generated method stub
		try{
			
			preparedStatement = conn.prepareStatement("Select * from PhongBan" );
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				String pb = resultSet.getString("maPhongBan") + "-" + resultSet.getString("TenPhongBan");
				cboPhongBan.addItem(pb);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

	private void ketNoiDuLieu() {
		// TODO Auto-generated method stub
		try{
			
			String dataBase = "csdl/dbSinhVien.accdb";
			String strConn = "jdbc:ucanaccess://" + dataBase;
			conn = DriverManager.getConnection(strConn);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		cboPhongBan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(cboPhongBan.getSelectedIndex()==-1) return;
				String mapb =  cboPhongBan.getSelectedItem().toString().split("-")[0];
				xemDanhSachNhanVienTheoPhongBan(mapb);
			}
		});
		
	}

	protected void xemDanhSachNhanVienTheoPhongBan(String mapb) {
		// TODO Auto-generated method stub
		try{
			String sql = "Select * from SinhVien where maPhongban = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, mapb);//Gán giá trị cho ?
			ResultSet resultSet = preparedStatement.executeQuery();
			Vector<String> vec = new Vector<>();
			while(resultSet.next()){
				vec.add(resultSet.getString("maPhongBan") + "-" + resultSet.getString("ten"));
				
			}
			listNhanVien.setListData(vec);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

	private void addControls() {
		// TODO Auto-generated method stub
		Container con = getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		JPanel pnPhongBan = new JPanel();
		pnPhongBan.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPhongBan = new JLabel("Chọn phòng ban");
		cboPhongBan = new JComboBox<>();
		cboPhongBan.setPreferredSize(new Dimension(300, 30));
		pnPhongBan.add(lblPhongBan);
		pnPhongBan.add(cboPhongBan);
		con.add(pnPhongBan);
				
		JPanel pnNhanVien  = new JPanel();
		pnNhanVien.setLayout(new BorderLayout());
		listNhanVien = new JList<>();
		JScrollPane sc  = new JScrollPane(listNhanVien,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnNhanVien.add(sc,BorderLayout.CENTER);
		con.add(pnNhanVien);
				
		
	}
	public void showWindow(){
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
