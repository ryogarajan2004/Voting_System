    package com.example.voting_system;

    import java.sql.*;

    public class Password {
        String Username;
        String Password;


        Password(String username, String password) {
            this.Username = username;
            this.Password = password;
        }

        boolean passcheck(Password pass) {
            boolean status = false;

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Voting", "yoga2405", "Rajanr*2405");
                Statement stmt = con.createStatement();

                String query = String.format("select COUNT(*) AS password_match from users where username='%s' and pass=PASSWORD('%s')", pass.Username, pass.Password);
                ResultSet set = stmt.executeQuery(query);

                // System.out.println(set.getInt(0));

                //System.out.println(set.first());
                set.next();

                System.out.println(set.getInt(1));
                if (set.getInt(1) == 1)
                    status = true;
                con.close();
                return status;


                /*if (set.getInt(1) == 1)
                    status =true;
                else
                    status =false;*/

            } catch (SQLException e) {
                System.out.println(e.toString());
                ;
            }

            return status;

        }

        public static void main(String[] args) throws SQLException {
            com.example.voting_system.Password password = new Password("yoga2405", "Rajanr*2405");
            password.passcheck(password);
            System.out.println(password.passcheck(password));
        }
    }
