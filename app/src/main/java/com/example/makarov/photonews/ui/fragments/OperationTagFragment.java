package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.database.TagDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.Tag;
import com.example.makarov.photonews.ui.activity.MainActivity;

import com.example.makarov.photonews.utils.SystemUtils;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.melnykov.fab.FloatingActionButton;

import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OperationTagFragment extends Fragment {

    @Bind(R.id.input_layout_tag)
    MaterialTextField mLayoutTag;
    @Bind(R.id.line_tag_search)
    EditText mLineTagSearch;
    @Bind(R.id.enter_search_btn)
    Button mEnterSearch;
    @Bind(R.id.add_tag_btn)
    FloatingActionButton mAddTag;

    @Inject
    TagDbAdapter mTagDbAdapter;

    private final View.OnClickListener mOnClickEnterSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tagSearch = mLineTagSearch.getText().toString();

            if (!TextUtils.isEmpty(tagSearch)) {
                openListPhotoResultTag(tagSearch);
            }
        }
    };

    private final View.OnClickListener mOnClickAddTagListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tagSearch = mLineTagSearch.getText().toString();

            if (!TextUtils.isEmpty(tagSearch)) {
                mTagDbAdapter.open().add(new Tag(tagSearch, new Date().getTime()));
                mTagDbAdapter.close();
            }
        }
    };

    private final TextWatcher mTextWatcherBanSpace = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            SystemUtils.banSpaceTextView(s);
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.operation_tag_fragment, null);
        ButterKnife.bind(this, v);
        AppInjector.get().inject(this);

        mEnterSearch.setOnClickListener(mOnClickEnterSearchListener);
        mAddTag.setOnClickListener(mOnClickAddTagListener);
        mLineTagSearch.addTextChangedListener(mTextWatcherBanSpace);

        return v;
    }

    private void openListPhotoResultTag(String lineTag) {
        Bundle bundle = new Bundle();
        bundle.putString(PhotoFragment.TAG_KEY, lineTag);
        ((MainActivity) getActivity()).openListResultTagFragment(bundle);
    }
}
