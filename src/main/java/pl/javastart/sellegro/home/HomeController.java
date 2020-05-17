package pl.javastart.sellegro.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.javastart.sellegro.auction.AuctionRepository;

@Controller
public class HomeController {

    private AuctionRepository auctionRepository;

    @Autowired
    public HomeController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cars", auctionRepository.findFirst4ByOrderByPriceDesc());
        return "home";
    }
}
