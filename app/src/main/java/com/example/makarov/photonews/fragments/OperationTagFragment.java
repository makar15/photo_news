package com.example.makarov.photonews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.activitys.MainActivity;

/**
 * Created by makarov on 10.12.15.
 */
public class OperationTagFragment extends Fragment implements View.OnClickListener {

    public static final String OPERATION_KEY = "operation";

    private EditText lineTagSearch;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.operation_tag_fragment, null);

        lineTagSearch = (EditText) v.findViewById(R.id.line_tag_search);
        v.findViewById(R.id.sign_tag);
        v.findViewById(R.id.enter_search_btn).setOnClickListener(this);

        lineTagSearch.addTextChangedListener(textWatcherBanSpace);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter_search_btn: {
                String mTagSearch = lineTagSearch.getText().toString();

                if (!TextUtils.isEmpty(mTagSearch)) {
                    openListPhotoResultTag(mTagSearch);
                }
            }
            break;

            //еще будет кнопка добавления этого тэга в список тэгов
            default:
                break;
        }
    }

    public void openListPhotoResultTag(String lineTag) {
        Bundle bundle = new Bundle();
        bundle.putString(OperationTagFragment.OPERATION_KEY, lineTag);
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

    //тут еще есть баг, в случае если переставить курсор печати
    private void banSpace(Editable s) {

        int length = s.length();

        if (length != 0) {
            String enteredText = s.toString();
            String lastChar = enteredText.substring(length - 1, length);
            if (lastChar.equals(" ")) {
                s.delete(length - 1, length);
            }
        }
    }
}
