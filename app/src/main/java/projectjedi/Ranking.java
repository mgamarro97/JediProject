package projectjedi;



    public class Ranking {
        private String user;
        private String score;

        Ranking(String user, String score){
            this.user = user;
            this.score = score;

        }
        Ranking(){

        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getScore() { return score;}

        public void setScore(String score) {
            this.score = score;
        }
    }

