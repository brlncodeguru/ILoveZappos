package Utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.lakshminarayanabr.ilovezappos.R;
import com.squareup.picasso.Picasso;

/**
 * Created by lakshminarayanabr on 2/6/17.
 */

public class BindingUtils {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url).error(R.mipmap.ic_launcher)
                .into(view);
    }

}
