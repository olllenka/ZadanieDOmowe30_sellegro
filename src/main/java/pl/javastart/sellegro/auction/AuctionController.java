package pl.javastart.sellegro.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class AuctionController {

    private AuctionRepository auctionRepository;

    @Autowired
    public AuctionController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
        auctionRepository.updateTitle();
    }

    @GetMapping("/auctions")
    public String auctions(Model model,
                           @RequestParam(required = false) String sort,
                           AuctionFilters auctionFilters) {

        List<Auction> auctions;
        if(sort != null) {
            switch (sort){
                case "title":
                    auctions =  auctionRepository.findAllByOrderByTitle();
                break;
                case "price":
                    auctions =  auctionRepository.findAllByOrderByPrice();
                    break;
                case "color":
                    auctions =  auctionRepository.findAllByOrderByColor();
                    break;
                case "endDate":
                    auctions =  auctionRepository.findAllByOrderByEndDate();
                    break;
                default:
                    auctions = auctionRepository.findAll();
            }
        }
        else if(auctionFilters.getTitle() == null){
            auctions = auctionRepository.findAll();
        }
        else {
            auctions = auctionRepository.filtered(auctionFilters.getTitle(), auctionFilters.getCarMaker(), auctionFilters.getCarModel(), auctionFilters.getColor());
        }

        model.addAttribute("cars", auctions);
        model.addAttribute("filters", auctionFilters);
        return "auctions";
    }
}
