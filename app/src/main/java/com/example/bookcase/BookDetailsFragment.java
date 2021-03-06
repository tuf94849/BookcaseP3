package com.example.bookcase;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.temple.audiobookplayer.AudiobookService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailsFragment extends Fragment {

    public BookDetailsFragment() {
        // Required empty public constructor
    }


    Context c;

    TextView tv;
    ImageView imageView;
    String bookSelected;
    String title;
    String author;
    String publish;
    public static final String BOOK_KEY = "myBook";
    Book pagerBooks;
    ImageButton playBTN;
    ImageButton pauseBTN;
    ImageButton stopBTN;
    SeekBar seekBar;
    ProgressBar progressBar;
    TextView progressText;

    private BookDetailsInterface mListener;

    public static BookDetailsFragment newInstance(Book bookList) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BOOK_KEY, bookList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            pagerBooks = getArguments().getParcelable(BOOK_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        tv = view.findViewById(R.id.bookTitle);
        imageView = view.findViewById(R.id.bookImage);

        playBTN = view.findViewById(R.id.playButton);
        pauseBTN = view.findViewById(R.id.pauseButton);
        stopBTN = view.findViewById(R.id.stopButton);
        seekBar = view.findViewById(R.id.seekBar);
        progressBar = view.findViewById(R.id.progressBar);
        progressText = view.findViewById(R.id.progressText);

        if(getArguments() != null) {
            displayBook(pagerBooks);
        }

        return view;
    }

    public void displayBook(final Book bookObj){
        author = bookObj.getAuthor();
        title = bookObj.getTitle(); publish = bookObj.getPublished();
        tv.setText(" \"" + title + "\" "); tv.append(" " + author); tv.append(" " + publish);
        tv.setTextSize(30);
        tv.setTextColor(Color.BLACK);
        String imageURL = bookObj.getCoverURL();
        Picasso.get().load(imageURL).into(imageView);

        playBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BookDetailsInterface)c).playBook(bookObj.getId());
            }
        });

        pauseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BookDetailsInterface)c).pauseBook();
            }
        });

        stopBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BookDetailsInterface)c).stopBook();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                ((BookDetailsInterface)c).seekBook(progress);
                progressText.setText(" " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //not needed
            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BookListFragment.BookInterface) {
            mListener = (BookDetailsInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.c = context;
    }

    public interface BookDetailsInterface{
        void playBook(int id);
        void pauseBook();
        void stopBook();
        void seekBook(int position);
    }





}
