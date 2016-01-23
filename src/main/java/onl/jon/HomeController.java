package onl.jon;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
public class HomeController {

    @Inject ConnectionRepository connectionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String helloFacebook(Model model) {
        Facebook facebook = getFacebookClient();
        if (facebook == null) {
            return "redirect:/connect/facebook";
        }
        User user = facebook.userOperations().getUserProfile();
        model.addAttribute("user", user);
        return "home";
    }

    private Facebook getFacebookClient() {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
        return connection != null ? connection.getApi() : null;
    }
}