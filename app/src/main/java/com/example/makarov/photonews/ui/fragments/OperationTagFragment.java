package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.models.Tag;
import com.example.makarov.photonews.ui.activity.MainActivity;

import java.util.Date;

public class OperationTagFragment extends Fragment implements View.OnClickListener {

    public static final String OPERATION_KEY = "operation";

    private EditText mLineTagSearch;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.operation_tag_fragment, null);

        mLineTagSearch = (EditText) v.findViewById(R.id.line_tag_search);
        v.findViewById(R.id.sign_tag);
        v.findViewById(R.id.enter_search_btn).setOnClickListener(this);
        v.findViewById(R.id.add_tag_btn).setOnClickListener(this);

        mLineTagSearch.addTextChangedListener(textWatcherBanSpace);

        return v;
    }

    @Override
    public void onClick(View v) {
        String tagSearch = mLineTagSearch.getText().toString();

        switch (v.getId()) {
            case R.id.enter_search_btn: {
                if (!TextUtils.isEmpty(tagSearch)) {
                    openListPhotoResultTag(tagSearch);
                }
            }
            break;

            case R.id.add_tag_btn: {

                if (!TextUtils.isEmpty(tagSearch)) {
                    PhotoNewsApp.getApp().getTagDbAdapter().open()
                            .add(new Tag(tagSearch, new Date().getTime()));
                }
            }
            break;

            default:
                break;
        }
    }

    private void openListPhotoResultTag(String lineTag) {
        Bundle bundle = new Bundle();
        bundle.putString(PhotoFragment.PHOTO_RESULT_TAG_KEY, lineTag);
        ((MainActivity) getActivity()).openListPhotoResultTagFragment(bundle);
    }

    private TextWatcher textWatcherBanSpace = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            banSpace(s);
        }
    };

    private void banSpace(Editable s) {

        int length = s.length();

        if (length != 0) {
            String enteredText = s.toString();
            char[] charArray = enteredText.toCharArray();
            for (int i = 0; i < length; i++) {
                if (charArray[i] == ' ') {
                    s.delete(i, i + 1);
                }
            }
        }
    }
}
