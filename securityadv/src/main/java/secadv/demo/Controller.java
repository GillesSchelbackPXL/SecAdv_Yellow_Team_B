package secadv.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/members")
    @PreAuthorize("hasAuthority('read:team')")
    public String giveMembers(){
        return "Ksenia, Ine, Xander, Gilles";
    }

    @GetMapping("/poem")
    @PreAuthorize("hasAuthority('read:admin')")
    public String givePoem(){
        return "Rozen zijn rood, viooltjes zijn blauw, neen ik ben geen pauw";
    }

    @GetMapping("/public")
    public String givePublic(){
        return "Public";
    }
}
