package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import data.KitchenDAO;
import data.MenuDAO;
import entities.Kitchen;

@Controller
public class KitchenController {

	@Autowired
	private KitchenDAO kitchenDao;

	@Autowired
	private MenuDAO menuDao;

	// ################ CREATE KITCHEN #####################

	@RequestMapping(path = "CreateKitchen.do", method = RequestMethod.GET)
	public ModelAndView createNewKitchenPathFromAdmin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("createkitchen");
		return mv;
	}

	@RequestMapping(path = "CreateKitchen.do", method = RequestMethod.POST)
	public ModelAndView createNewKitchen(Kitchen kitchen) {
		ModelAndView mv = new ModelAndView();
		kitchenDao.createKitchen(kitchen); 
		mv.addObject("kitchens", kitchenDao.listOfKitchen());
		mv.setViewName("admin");
		return mv;
	}

	// ################### READ KITCHEN #########################

	@RequestMapping(path = "KitchenController.do", method = RequestMethod.GET)
	public ModelAndView getAllKitchen(@RequestParam("action") String action, @RequestParam("kitchenId") int id) {
		ModelAndView mv = new ModelAndView();
		if (action.equalsIgnoreCase("all")) {
			mv.setViewName("adminkitchen");
			mv.addObject("selectedMenu", menuDao.getAllItemsFromKitchen(id));
		} else if (action.equalsIgnoreCase("appetizer")) {
			mv.setViewName("adminkitchen");
			mv.addObject("selectedMenu", menuDao.getAllMenuItemsFromAppetizers(id));
		} else if (action.equalsIgnoreCase("entree")) {
			mv.setViewName("adminkitchen");
			mv.addObject("selectedMenu", menuDao.getAllMenuItemsFromEntrees(id));
		} else if (action.equalsIgnoreCase("dessert")) {
			mv.setViewName("adminkitchen");
			mv.addObject("selectedMenu", menuDao.getAllMenuItemsFromDesserts(id));
		} else if (action.equalsIgnoreCase("drink")) {
			mv.setViewName("adminkitchen");
			mv.addObject("selectedMenu", menuDao.getAllMenuItemsFromDrinks(id));
		}
		return mv;
	}

	// ################## UPDATE KITCHEN #######################

	@RequestMapping(path = "UpdateKitchen.do", method = RequestMethod.GET)
	public ModelAndView updateKitchenPathFromAdmin(@RequestParam("kitchenId")int kitchenId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("adminupdatepage");
		mv.addObject("kitchen", kitchenDao.getKitchenById(kitchenId));
		return mv;
	}
	
	@RequestMapping(path = "UpdateKitchen.do", method = RequestMethod.POST)
	public ModelAndView updateKitchen(@RequestParam("id")int id, Kitchen kitchen) {
		ModelAndView mv = new ModelAndView();
		kitchenDao.updateKitchen(id, kitchen);
		mv.addObject("kitchens", kitchenDao.listOfKitchen());
		mv.setViewName("admin");
		return mv;
	}

	// ################### DELETE KITCHEN #######################

	@RequestMapping(path = "RemoveKitchen.do", method = RequestMethod.GET)
	public ModelAndView removeKitchen(@RequestParam("kitchenId") int id) {
		ModelAndView mv = new ModelAndView();
		kitchenDao.removeKitchenAndMenuItems(id);
		mv.addObject("kitchens", kitchenDao.listOfKitchen());
		mv.setViewName("admin");
		return mv;
	}

}
