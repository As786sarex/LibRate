package myfab.wildcardenter.com.first_app.activities;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import myfab.wildcardenter.com.first_app.R;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        NestedScrollView flHolder = findViewById(R.id.about);
        AboutBuilder builder = AboutBuilder.with(this)
                .setAppIcon(R.drawable.logo)
                .setAppName(R.string.app_name)
                .setPhoto(R.mipmap.profile_picture)
                .setCover(R.mipmap.profile_cover)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName("Asif Mondal")
                .setSubTitle("Mobile Developer")
                .setLinksColumnsCount(4)
                .setBrief("I'm warmed of mobile technologies. Ideas maker, curious and nature lover.")
                .addGooglePlayStoreLink("8002078663318221363")
                .addGitHubLink("as786sarex")
                .addBitbucketLink("as786sarex")
                .addFacebookLink("Asif mondal")
                .addTwitterLink("@as786sarex")
                .addInstagramLink("as786sarex")
                .addGooglePlusLink("+JuniorVansuita")
                .addYoutubeChannelLink("V-ClipMeta")
                .addDribbbleLink("user")
                .addLinkedInLink("arleu-cezar-vansuita-júnior-83769271")
                .addEmailLink("As786sarex@gmail.com")
                .addWhatsappLink("Asif", "+9153872104")
                .addSkypeLink("user")
                .addGoogleLink("user")
                .addAndroidLink("user")
                .addWebsiteLink("site")
                .addFiveStarsAction()
                .addMoreFromMeAction("Vansuita")
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addUpdateAction()
                .setActionsColumnsCount(2)
                .addFeedbackAction("as786sarex@gmail.com")
                .addPrivacyPolicyAction("http://www.docracy.com/2d0kis6uc2");
        AboutView view=builder.build();
        flHolder.addView(view);

    }
}
