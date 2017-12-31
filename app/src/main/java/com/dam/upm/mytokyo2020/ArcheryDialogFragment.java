package com.dam.upm.mytokyo2020;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Angel on 31/12/2017.
 */

public class ArcheryDialogFragment extends DialogFragment {

    //MapFragment mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.archery_fragment_dialog,container,false);
        getDialog().setTitle("Archery Rules");
        TextView t = (TextView)rootView.findViewById(R.id.info);
        t.setText("There are several stages to the competition, which likely will span eight days. Both men and women will use recurve bows to shoot at targets 70 meters away, which is about 77 yards.\n\n" +
                "Each target features 10 scoring rings – worth 1-10 points – divided among five colors. Working from the outside in, the two outer-most rings are white and are worth 1 and 2 points; the next two rings are black and are worth 3 and 4 points; two blue rings are worth 5 and 6 points; two red rings are worth 7 and 8 points; and the gold center rings are worth 9 and 10 points.\n\n" +
                "The target face is 1.22 meters – about 48 inches – in diameter, which looks like it’s about the size of a thumbtack to the archers standing 70 meters away. The inner-most, gold ring, which is worth 10 points, is 12.2 cm – or 4.8 inches – in diameter. That’s about the size of a coffee can lid, which, from 70 yards away, looks like a dot in the middle of the thumbtack.\n\n" +
        "All archers will start the tournament by shooting a round of 72 arrows. A perfect score for that round is 720. In the men’s division, Im Dong-hyun of South Korea holds the Olympic record with a score of 699. He posted that score during the London Olympics in 2012. For the women, Lina Herasymenko (1996) of Ukraine and Park Sung Hyun (2008) of South Korea are tied for the record at 673.\n\n" +
        "After all archers shoot their initial rounds, they will be ranked by score in men’s and women’s divisions. The top 64 in each division qualify to advance in the competition.\n\n" +
        "Those 64 archers will then compete in head-to-head, elimination matches. Brackets will be established for the men and women based on their rankings. For instance, the archer ranked No. 1 will shoot against archer No. 64; No. 2 vs. No. 63; and so forth. Those elimination matches ultimately will produce two undefeated archers who will compete for the gold and silver medals. The bronze will be awarded to the winner of a match between the last two archers defeated by the two finalists.\n\n" +
        "In the match-play competition, archers will shoot three-arrow groups called “ends.” The total score of each end from each archer will be matched. The high-score holder gets 2 points for winning that end, while the loser gets 0. A tie results in a 1-1 split. The match winner is the one who earns six points first. If there’s a tie at the conclusion of five ends, then there’s a one-arrow shoot-off. The winner is the archer with the arrow closest to the center.\n\n" +
        "The team competition is open only to those countries with three men shooting in the men’s division, and/or three women in the women’s division. The cumulative individual qualifying scores will be used to rank the teams. The top 12 teams advance.\n\n" +
        "The top four teams receive byes to the quarter finals. The remaining eight teams are entered into a match-play, elimination bracket. In head-to head competition, each team shoots 24 arrows for score. The team with the highest score advances. If there’s a tie, a three-arrow shoot-off takes place.\n\n" +
        "The top four teams from the initial match-play round advance to shoot against the overall top-four teams. Again, the head-to-head matches then produce two final matches, which determine who gets the gold, silver and bronze medals.\n\n");
        Button dismiss = (Button)rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootView;
    }
}
