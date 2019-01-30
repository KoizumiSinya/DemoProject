package jp.sinya.swipeback.demo;

public class Library2ItemFragment extends BaseFragment {
    @Override
    protected String getTextContent() {
        return "This is " + ((Library2FragmentActivity) getActivity()).getCount() + " Fragment";
    }

    @Override
    protected void onClickBack() {
    }
}
