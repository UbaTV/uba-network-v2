package xyz.ubatv.hub.rankSystem;

import java.util.ArrayList;
import java.util.List;

public class RankManager {

    public Ranks idToRank(int id){
        if(id == 1) return Ranks.VIP;
        else if(id == 2) return Ranks.VIP_PLUS;
        else if(id == 3) return Ranks.MVP;
        else if(id == 4) return Ranks.MVP_PLUS;
        else if(id == 5) return Ranks.INFLUENCER;
        else if(id == 6) return Ranks.BUILDER;
        else if(id == 7) return Ranks.MOD;
        else if(id == 8) return Ranks.ADMIN;
        else if(id == 9) return Ranks.DEV;
        else if(id == 10) return Ranks.CEO;
        else return Ranks.DEFAULT;
    }

    public int rankToId(Ranks rank){
        if(rank.equals(Ranks.VIP)) return 1;
        else if(rank.equals(Ranks.VIP_PLUS)) return 2;
        else if(rank.equals(Ranks.MVP)) return 3;
        else if(rank.equals(Ranks.MVP_PLUS)) return 4;
        else if(rank.equals(Ranks.INFLUENCER)) return 5;
        else if(rank.equals(Ranks.BUILDER)) return 6;
        else if(rank.equals(Ranks.MOD)) return 7;
        else if(rank.equals(Ranks.ADMIN)) return 8;
        else if(rank.equals(Ranks.DEV)) return 9;
        else if(rank.equals(Ranks.CEO)) return 10;
        else return 0;
    }

    public List<Ranks> getListRanks(){
        List<Ranks> ranksList = new ArrayList<>();

        ranksList.add(Ranks.DEFAULT);

        ranksList.add(Ranks.VIP);
        ranksList.add(Ranks.VIP_PLUS);
        ranksList.add(Ranks.MVP);
        ranksList.add(Ranks.MVP_PLUS);

        ranksList.add(Ranks.INFLUENCER);

        ranksList.add(Ranks.BUILDER);
        ranksList.add(Ranks.MOD);
        ranksList.add(Ranks.ADMIN);
        ranksList.add(Ranks.DEV);
        ranksList.add(Ranks.CEO);

        return ranksList;
    }
}
