import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class App {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/perpustakaan";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    public static void main(String[] args) {

        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertBuku();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateBuku();
                    break;
                case 4:
                    deleteBuku();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showData() {
        String sql = "SELECT * FROM buku";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+--------------------------------+");
            System.out.println("|    DATA BUKU DI PERPUSTAKAAN   |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                int Id_Buku = rs.getInt("Id_Buku");
                String Judul_Buku = rs.getString("Judul_Buku");
                String Penulis_Buku = rs.getString("Penulis_Buku");

                
                System.out.println(String.format("%d. %s -- (%s)", Id_Buku, Judul_Buku, Penulis_Buku));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertBuku() {
        try {
            // ambil input dari user
            //tambahkan id_buku
            System.out.print("Id_Buku: ");
            int Id_Buku = Integer.parseInt(input.readLine());
            System.out.print("Judul_Buku: ");
            String Judul_Buku = input.readLine().trim();
            System.out.print("Penulis_Buku: ");
            String Penulis_Buku = input.readLine().trim();
 
            // query simpan
            String sql = "INSERT INTO buku (Judul_Buku, Penulis_Buku, Id_buku) VALUE('%s', '%s' , '%d')";
            sql = String.format(sql, Judul_Buku, Penulis_Buku, Id_Buku);

            // simpan buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updateBuku() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int Id_Buku = Integer.parseInt(input.readLine());
            System.out.print("Judul_Buku: ");
            String Judul_Buku = input.readLine().trim();
            System.out.print("Penulis_Buku: ");
            String Penulis_Buku = input.readLine().trim();

            // query update
            String sql = "UPDATE buku SET Judul_Buku='%s', Penulis_Buku='%s' WHERE Id_Buku=%d";
            sql = String.format(sql, Judul_Buku, Penulis_Buku, Id_Buku);

            // update data buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteBuku() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int Id_Buku = Integer.parseInt(input.readLine());
            
            // buat query hapus
            String sql = String.format("DELETE FROM buku WHERE Id_Buku=%d", Id_Buku);

            // hapus data
            stmt.execute(sql);
            
            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}