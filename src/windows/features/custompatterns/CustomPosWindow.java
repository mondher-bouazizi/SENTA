package windows.features.custompatterns;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import main.items.Features;
import windows.others.AlertBox;

public class CustomPosWindow implements Initializable {
	
	protected static int numberOfCategories;
	
	ArrayList<ComboBox<String>> boxes;
	
	@FXML ComboBox<String> cc;
	@FXML ComboBox<String> cd;
	@FXML ComboBox<String> ex;
	@FXML ComboBox<String> fw;
	@FXML ComboBox<String> in;
	@FXML ComboBox<String> jj;
	@FXML ComboBox<String> jjr;
	@FXML ComboBox<String> jjs;
	@FXML ComboBox<String> ls;
	@FXML ComboBox<String> md;
	@FXML ComboBox<String> nn;
	@FXML ComboBox<String> nns;
	@FXML ComboBox<String> nnp;
	@FXML ComboBox<String> nnps;
	@FXML ComboBox<String> pdt;
	@FXML ComboBox<String> pos;
	@FXML ComboBox<String> prp;
	@FXML ComboBox<String> prps;
	@FXML ComboBox<String> rb;
	@FXML ComboBox<String> rbr;
	@FXML ComboBox<String> rbs;
	@FXML ComboBox<String> rp;
	@FXML ComboBox<String> sym;
	@FXML ComboBox<String> to;
	@FXML ComboBox<String> uh;
	@FXML ComboBox<String> vb;
	@FXML ComboBox<String> vbd;
	@FXML ComboBox<String> vbg;
	@FXML ComboBox<String> vbn;
	@FXML ComboBox<String> vbp;
	@FXML ComboBox<String> vbz;
	@FXML ComboBox<String> wdt;
	@FXML ComboBox<String> wp;
	@FXML ComboBox<String> wps;
	@FXML ComboBox<String> wrb;
	@FXML ComboBox<String> dt;
	@FXML ComboBox<String> dot;
	
	@FXML Button def;
	@FXML Button ok;
	@FXML Button cancel;
	
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		if (numberOfCategories==1) {
			handleOneCategories();
		} else if (numberOfCategories==2) {
			handleTwoCategories();
		} else if (numberOfCategories==3) {
			handleThreeCategories();
		} else if (numberOfCategories==4) {
			handleThreeCategories();
		}
	}
	
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		
		boolean fine = true;
		
		for (ComboBox<String> box : boxes) {
			if (box.getValue()==null) {
				fine = false;
				break;
			}
		}
		
		if (fine) {
			
			// Using the array TODO remove this when done!
			Features.Categories[] categories = new Features.Categories[boxes.size()];
			int i = 0;
			for (ComboBox<String> box : boxes) {
				if (box.getValue().equals("Category 1")) {
					categories[i] = Features.Categories.cat1;
				} else if (box.getValue().equals("Category 2")) {
					categories[i] = Features.Categories.cat2;
				} else if (box.getValue().equals("Category 3")) {
					categories[i] = Features.Categories.cat3;
				} else if (box.getValue().equals("Category 4")) {
					categories[i] = Features.Categories.cat4;
				}
				i++;
			}
			Features.setCategories(categories);
			
			// Using the HashMap
			Features.setCategoriesMap(setCategories());
			
			CustomizePos.stage.close();
		} else {
			AlertBox.display("Error", "Please choose a category for each PoS-Tag (You can click default to make sure all of them are fine)", "OK");
		}
	}
	
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		CustomizePos.stage.close();
	}
	
	
	
	
	
	
	public static void setParameters (int numberOfCategories) {
		CustomPosWindow.numberOfCategories = numberOfCategories;
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		boxes = new  ArrayList<>();
		
		boxes.addAll(Arrays.asList(cc, cd, ex, fw, in, jj, jjr, jjs, ls, md, nn, nns, nnp, nnps, pdt, pos, prp, 
				prps, rb, rbr, rbs, rp, sym, to, uh, vb, vbd, vbg, vbn, vbp, vbz, wdt, wp, wps, wrb, dt, dot));
		
		for (ComboBox<String> box : boxes) {
			box.setPromptText("Category…");
			if (box.getItems().isEmpty()) {
				for (int i=0; i<numberOfCategories; i++) {
					int c = i+1;
					String cat = "Category " + c;
					box.getItems().add(cat);
				}
			}
			
		}
		
		if (Features.getCategories()!=null) {
			int i = 0;
			for (ComboBox<String> box : boxes) {
				if (Features.getCategories()[i].equals(Features.Categories.cat1)) {
					box.setValue("Category 1");
				} else if (Features.getCategories()[i].equals(Features.Categories.cat2)) {
					box.setValue("Category 2");
				} else if (Features.getCategories()[i].equals(Features.Categories.cat3)) {
					box.setValue("Category 3");
				} else if (Features.getCategories()[i].equals(Features.Categories.cat4)) {
					box.setValue("Category 4");
				}
				i++;
			}
			
		}
		
		
		
		
	}
	
	
	private void handleOneCategories() {
		for (ComboBox<String> box : boxes) {
			box.setValue("Category 1");
		}
	}
	
	private void handleTwoCategories() {
		
		// First Category contains nouns, verbs, adjectives and adverbs
		// nouns
		nn.setValue("Category 1");
		nns.setValue("Category 1");
		// verbs
		vb.setValue("Category 1");
		vbd.setValue("Category 1");
		vbg.setValue("Category 1");
		vbn.setValue("Category 1");
		vbp.setValue("Category 1");
		vbz.setValue("Category 1");
		// adjectives
		jj.setValue("Category 1");
		jjr.setValue("Category 1");
		jjs.setValue("Category 1");
		//adverbs
		rb.setValue("Category 1");
		rbr.setValue("Category 1");
		rbs.setValue("Category 1");
		
		// Second Category contains The rest
		cc.setValue("Category 2");
		cd.setValue("Category 2");
		ex.setValue("Category 2");
		fw.setValue("Category 2");
		in.setValue("Category 2");
		ls.setValue("Category 2");
		md.setValue("Category 2");
		nnp.setValue("Category 2");
		nnps.setValue("Category 2");
		pdt.setValue("Category 2");
		pos.setValue("Category 2");
		prp.setValue("Category 2");
		prps.setValue("Category 2");
		rp.setValue("Category 2");
		sym.setValue("Category 2");
		to.setValue("Category 2");
		uh.setValue("Category 2");
		wdt.setValue("Category 2");
		wp.setValue("Category 2");
		wps.setValue("Category 2");
		wrb.setValue("Category 2");
		dt.setValue("Category 2");
		dot.setValue("Category 2");
	}
	
	private void handleThreeCategories() {
		
		// First Category contains nouns, verbs, adjectives and adverbs
		// nouns
		nn.setValue("Category 1");
		nns.setValue("Category 1");
		// verbs
		vb.setValue("Category 1");
		vbd.setValue("Category 1");
		vbg.setValue("Category 1");
		vbn.setValue("Category 1");
		vbp.setValue("Category 1");
		vbz.setValue("Category 1");
		// adjectives
		jj.setValue("Category 1");
		jjr.setValue("Category 1");
		jjs.setValue("Category 1");
		//adverbs
		rb.setValue("Category 1");
		rbr.setValue("Category 1");
		rbs.setValue("Category 1");
		
		// Second Category contains symbols, numbers, interjections and foreign words
		cd.setValue("Category 2");
		fw.setValue("Category 2");
		ls.setValue("Category 2");
		sym.setValue("Category 2");
		uh.setValue("Category 2");
		
		// Thrid Category contains The rest
		cc.setValue("Category 3");
		ex.setValue("Category 3");
		in.setValue("Category 3");
		md.setValue("Category 3");
		nnp.setValue("Category 3");
		nnps.setValue("Category 3");
		pdt.setValue("Category 3");
		pos.setValue("Category 3");
		prp.setValue("Category 3");
		prps.setValue("Category 3");
		rp.setValue("Category 3");
		to.setValue("Category 3");
		wdt.setValue("Category 3");
		wp.setValue("Category 3");
		wps.setValue("Category 3");
		wrb.setValue("Category 3");
		dt.setValue("Category 3");
		dot.setValue("Category 3");
	}
	
	private HashMap<String, Features.Categories> setCategories() {
		HashMap<String, Features.Categories> categoriesMap = new HashMap<>();
		
		
		// --- CC --- //
		if (cc.getValue()!=null) {
			if (cc.getValue().equals("Category 1")) {
				categoriesMap.put("CC", Features.Categories.cat1);
			} else if (cc.getValue().equals("Category 2")) {
				categoriesMap.put("CC", Features.Categories.cat2);
			} else if (cc.getValue().equals("Category 3")) {
				categoriesMap.put("CC", Features.Categories.cat3);
			} else if (cc.getValue().equals("Category 4")) {
				categoriesMap.put("CC", Features.Categories.cat4);
			} 
		}
		
		// --- CD --- //
		if (cd.getValue()!=null) {
			if (cd.getValue().equals("Category 1")) {
				categoriesMap.put("CD", Features.Categories.cat1);
			} else if (cd.getValue().equals("Category 2")) {
				categoriesMap.put("CD", Features.Categories.cat2);
			} else if (cd.getValue().equals("Category 3")) {
				categoriesMap.put("CD", Features.Categories.cat3);
			} else if (cd.getValue().equals("Category 4")) {
				categoriesMap.put("CD", Features.Categories.cat4);
			} 
		}
		
		// --- EX --- //
		if (ex.getValue()!=null) {
			if (ex.getValue().equals("Category 1")) {
				categoriesMap.put("EX", Features.Categories.cat1);
			} else if (ex.getValue().equals("Category 2")) {
				categoriesMap.put("EX", Features.Categories.cat2);
			} else if (ex.getValue().equals("Category 3")) {
				categoriesMap.put("EX", Features.Categories.cat3);
			} else if (ex.getValue().equals("Category 4")) {
				categoriesMap.put("EX", Features.Categories.cat4);
			} 
		}
		
		// --- FW --- //
		if (fw.getValue()!=null) {
			if (fw.getValue().equals("Category 1")) {
				categoriesMap.put("FW", Features.Categories.cat1);
			} else if (fw.getValue().equals("Category 2")) {
				categoriesMap.put("FW", Features.Categories.cat2);
			} else if (fw.getValue().equals("Category 3")) {
				categoriesMap.put("FW", Features.Categories.cat3);
			} else if (fw.getValue().equals("Category 4")) {
				categoriesMap.put("FW", Features.Categories.cat4);
			} 
		}
		
		// --- IN --- //
		if (in.getValue()!=null) {
			if (in.getValue().equals("Category 1")) {
				categoriesMap.put("IN", Features.Categories.cat1);
			} else if (in.getValue().equals("Category 2")) {
				categoriesMap.put("IN", Features.Categories.cat2);
			} else if (in.getValue().equals("Category 3")) {
				categoriesMap.put("IN", Features.Categories.cat3);
			} else if (in.getValue().equals("Category 4")) {
				categoriesMap.put("IN", Features.Categories.cat4);
			} 
		}
		
		// --- JJ --- //
		if (jj.getValue()!=null) {
			if (jj.getValue().equals("Category 1")) {
				categoriesMap.put("JJ", Features.Categories.cat1);
			} else if (jj.getValue().equals("Category 2")) {
				categoriesMap.put("JJ", Features.Categories.cat2);
			} else if (jj.getValue().equals("Category 3")) {
				categoriesMap.put("JJ", Features.Categories.cat3);
			} else if (jj.getValue().equals("Category 4")) {
				categoriesMap.put("JJ", Features.Categories.cat4);
			} 
		}

		// --- JJR --- //
		if (jjr.getValue()!=null) {
			if (jjr.getValue().equals("Category 1")) {
				categoriesMap.put("JJR", Features.Categories.cat1);
			} else if (jjr.getValue().equals("Category 2")) {
				categoriesMap.put("JJR", Features.Categories.cat2);
			} else if (jjr.getValue().equals("Category 3")) {
				categoriesMap.put("JJR", Features.Categories.cat3);
			} else if (jjr.getValue().equals("Category 4")) {
				categoriesMap.put("JJR", Features.Categories.cat4);
			} 
		}
		
		// --- JJS --- //
		if (jjs.getValue()!=null) {
			if (jjs.getValue().equals("Category 1")) {
				categoriesMap.put("JJS", Features.Categories.cat1);
			} else if (jjs.getValue().equals("Category 2")) {
				categoriesMap.put("JJS", Features.Categories.cat2);
			} else if (jjs.getValue().equals("Category 3")) {
				categoriesMap.put("JJS", Features.Categories.cat3);
			} else if (jjs.getValue().equals("Category 4")) {
				categoriesMap.put("JJS", Features.Categories.cat4);
			} 
		}

		// --- LS --- //
		if (ls.getValue()!=null) {
			if (ls.getValue().equals("Category 1")) {
				categoriesMap.put("LS", Features.Categories.cat1);
			} else if (ls.getValue().equals("Category 2")) {
				categoriesMap.put("LS", Features.Categories.cat2);
			} else if (ls.getValue().equals("Category 3")) {
				categoriesMap.put("LS", Features.Categories.cat3);
			} else if (ls.getValue().equals("Category 4")) {
				categoriesMap.put("LS", Features.Categories.cat4);
			} 
		}
		
		// --- MD --- //
		if (md.getValue()!=null) {
			if (md.getValue().equals("Category 1")) {
				categoriesMap.put("MD", Features.Categories.cat1);
			} else if (md.getValue().equals("Category 2")) {
				categoriesMap.put("MD", Features.Categories.cat2);
			} else if (md.getValue().equals("Category 3")) {
				categoriesMap.put("MD", Features.Categories.cat3);
			} else if (md.getValue().equals("Category 4")) {
				categoriesMap.put("MD", Features.Categories.cat4);
			} 
		}
		
		// --- NN --- //
		if (nn.getValue()!=null) {
			if (nn.getValue().equals("Category 1")) {
				categoriesMap.put("NN", Features.Categories.cat1);
			} else if (nn.getValue().equals("Category 2")) {
				categoriesMap.put("NN", Features.Categories.cat2);
			} else if (nn.getValue().equals("Category 3")) {
				categoriesMap.put("NN", Features.Categories.cat3);
			} else if (nn.getValue().equals("Category 4")) {
				categoriesMap.put("NN", Features.Categories.cat4);
			} 
		}

		// --- NNS --- //
		if (nns.getValue()!=null) {
			if (nns.getValue().equals("Category 1")) {
				categoriesMap.put("NNS", Features.Categories.cat1);
			} else if (nns.getValue().equals("Category 2")) {
				categoriesMap.put("NNS", Features.Categories.cat2);
			} else if (nns.getValue().equals("Category 3")) {
				categoriesMap.put("NNS", Features.Categories.cat3);
			} else if (nns.getValue().equals("Category 4")) {
				categoriesMap.put("NNS", Features.Categories.cat4);
			} 
		}
		
		// --- NNP --- //
		if (nnp.getValue()!=null) {
			if (nnp.getValue().equals("Category 1")) {
				categoriesMap.put("NNP", Features.Categories.cat1);
			} else if (nnp.getValue().equals("Category 2")) {
				categoriesMap.put("NNP", Features.Categories.cat2);
			} else if (nnp.getValue().equals("Category 3")) {
				categoriesMap.put("NNP", Features.Categories.cat3);
			} else if (nnp.getValue().equals("Category 4")) {
				categoriesMap.put("NNP", Features.Categories.cat4);
			} 
		}
		
		// --- NNPS --- //
		if (nnps.getValue()!=null) {
			if (nnps.getValue().equals("Category 1")) {
				categoriesMap.put("NNPS", Features.Categories.cat1);
			} else if (nnps.getValue().equals("Category 2")) {
				categoriesMap.put("NNPS", Features.Categories.cat2);
			} else if (nnps.getValue().equals("Category 3")) {
				categoriesMap.put("NNPS", Features.Categories.cat3);
			} else if (nnps.getValue().equals("Category 4")) {
				categoriesMap.put("NNPS", Features.Categories.cat4);
			} 
		}
		
		// --- PDT --- //
		if (pdt.getValue()!=null) {
			if (pdt.getValue().equals("Category 1")) {
				categoriesMap.put("PDT", Features.Categories.cat1);
			} else if (pdt.getValue().equals("Category 2")) {
				categoriesMap.put("PDT", Features.Categories.cat2);
			} else if (pdt.getValue().equals("Category 3")) {
				categoriesMap.put("PDT", Features.Categories.cat3);
			} else if (pdt.getValue().equals("Category 4")) {
				categoriesMap.put("PDT", Features.Categories.cat4);
			} 
		}
		
		// --- POS --- //
		if (pos.getValue()!=null) {
			if (pos.getValue().equals("Category 1")) {
				categoriesMap.put("POS", Features.Categories.cat1);
			} else if (pos.getValue().equals("Category 2")) {
				categoriesMap.put("POS", Features.Categories.cat2);
			} else if (pos.getValue().equals("Category 3")) {
				categoriesMap.put("POS", Features.Categories.cat3);
			} else if (pos.getValue().equals("Category 4")) {
				categoriesMap.put("POS", Features.Categories.cat4);
			} 
		}
		
		// --- PRP --- //
		if (prp.getValue()!=null) {
			if (prp.getValue().equals("Category 1")) {
				categoriesMap.put("PRP", Features.Categories.cat1);
			} else if (prp.getValue().equals("Category 2")) {
				categoriesMap.put("PRP", Features.Categories.cat2);
			} else if (prp.getValue().equals("Category 3")) {
				categoriesMap.put("PRP", Features.Categories.cat3);
			} else if (prp.getValue().equals("Category 4")) {
				categoriesMap.put("PRP", Features.Categories.cat4);
			} 
		}

		// --- PRPS --- //
		if (prps.getValue()!=null) {
			if (prps.getValue().equals("Category 1")) {
				categoriesMap.put("PRP$", Features.Categories.cat1);
			} else if (prps.getValue().equals("Category 2")) {
				categoriesMap.put("PRP$", Features.Categories.cat2);
			} else if (prps.getValue().equals("Category 3")) {
				categoriesMap.put("PRP$", Features.Categories.cat3);
			} else if (prps.getValue().equals("Category 4")) {
				categoriesMap.put("PRP$", Features.Categories.cat4);
			} 
		}
		
		// --- RB --- //
		if (rb.getValue()!=null) {
			if (rb.getValue().equals("Category 1")) {
				categoriesMap.put("RB", Features.Categories.cat1);
			} else if (rb.getValue().equals("Category 2")) {
				categoriesMap.put("RB", Features.Categories.cat2);
			} else if (rb.getValue().equals("Category 3")) {
				categoriesMap.put("RB", Features.Categories.cat3);
			} else if (rb.getValue().equals("Category 4")) {
				categoriesMap.put("RB", Features.Categories.cat4);
			} 
		}

		// --- RBR --- //
		if (rbr.getValue()!=null) {
			if (rbr.getValue().equals("Category 1")) {
				categoriesMap.put("RBR", Features.Categories.cat1);
			} else if (rbr.getValue().equals("Category 2")) {
				categoriesMap.put("RBR", Features.Categories.cat2);
			} else if (rbr.getValue().equals("Category 3")) {
				categoriesMap.put("RBR", Features.Categories.cat3);
			} else if (rbr.getValue().equals("Category 4")) {
				categoriesMap.put("RBR", Features.Categories.cat4);
			} 
		}
		
		// --- RBS --- //
		if (rbs.getValue()!=null) {
			if (rbs.getValue().equals("Category 1")) {
				categoriesMap.put("RBS", Features.Categories.cat1);
			} else if (rbs.getValue().equals("Category 2")) {
				categoriesMap.put("RBS", Features.Categories.cat2);
			} else if (rbs.getValue().equals("Category 3")) {
				categoriesMap.put("RBS", Features.Categories.cat3);
			} else if (rbs.getValue().equals("Category 4")) {
				categoriesMap.put("RBS", Features.Categories.cat4);
			} 
		}
		
		// --- RP --- //
		if (rp.getValue()!=null) {
			if (rp.getValue().equals("Category 1")) {
				categoriesMap.put("RP", Features.Categories.cat1);
			} else if (rp.getValue().equals("Category 2")) {
				categoriesMap.put("RP", Features.Categories.cat2);
			} else if (rp.getValue().equals("Category 3")) {
				categoriesMap.put("RP", Features.Categories.cat3);
			} else if (rp.getValue().equals("Category 4")) {
				categoriesMap.put("RP", Features.Categories.cat4);
			} 
		}
		
		// --- SYM --- //
		if (sym.getValue()!=null) {
			if (sym.getValue().equals("Category 1")) {
				categoriesMap.put("SYM", Features.Categories.cat1);
			} else if (sym.getValue().equals("Category 2")) {
				categoriesMap.put("SYM", Features.Categories.cat2);
			} else if (sym.getValue().equals("Category 3")) {
				categoriesMap.put("SYM", Features.Categories.cat3);
			} else if (sym.getValue().equals("Category 4")) {
				categoriesMap.put("SYM", Features.Categories.cat4);
			} 
		}
		
		// --- TO --- //
		if (to.getValue()!=null) {
			if (to.getValue().equals("Category 1")) {
				categoriesMap.put("TO", Features.Categories.cat1);
			} else if (to.getValue().equals("Category 2")) {
				categoriesMap.put("TO", Features.Categories.cat2);
			} else if (to.getValue().equals("Category 3")) {
				categoriesMap.put("TO", Features.Categories.cat3);
			} else if (to.getValue().equals("Category 4")) {
				categoriesMap.put("TO", Features.Categories.cat4);
			} 
		}
		
		// --- UH --- //
		if (uh.getValue()!=null) {
			if (uh.getValue().equals("Category 1")) {
				categoriesMap.put("UH", Features.Categories.cat1);
			} else if (uh.getValue().equals("Category 2")) {
				categoriesMap.put("UH", Features.Categories.cat2);
			} else if (uh.getValue().equals("Category 3")) {
				categoriesMap.put("UH", Features.Categories.cat3);
			} else if (uh.getValue().equals("Category 4")) {
				categoriesMap.put("UH", Features.Categories.cat4);
			} 
		}
		
		// --- VB --- //
		if (vb.getValue()!=null) {
			if (vb.getValue().equals("Category 1")) {
				categoriesMap.put("VB", Features.Categories.cat1);
			} else if (vb.getValue().equals("Category 2")) {
				categoriesMap.put("VB", Features.Categories.cat2);
			} else if (vb.getValue().equals("Category 3")) {
				categoriesMap.put("VB", Features.Categories.cat3);
			} else if (vb.getValue().equals("Category 4")) {
				categoriesMap.put("VB", Features.Categories.cat4);
			} 
		}

		// --- VBD --- //
		if (vbd.getValue()!=null) {
			if (vbd.getValue().equals("Category 1")) {
				categoriesMap.put("VBD", Features.Categories.cat1);
			} else if (vbd.getValue().equals("Category 2")) {
				categoriesMap.put("VBD", Features.Categories.cat2);
			} else if (vbd.getValue().equals("Category 3")) {
				categoriesMap.put("VBD", Features.Categories.cat3);
			} else if (vbd.getValue().equals("Category 4")) {
				categoriesMap.put("VBD", Features.Categories.cat4);
			} 
		}
		
		// --- VBG --- //
		if (vbg.getValue()!=null) {
			if (vbg.getValue().equals("Category 1")) {
				categoriesMap.put("VBG", Features.Categories.cat1);
			} else if (vbg.getValue().equals("Category 2")) {
				categoriesMap.put("VBG", Features.Categories.cat2);
			} else if (vbg.getValue().equals("Category 3")) {
				categoriesMap.put("VBG", Features.Categories.cat3);
			} else if (vbg.getValue().equals("Category 4")) {
				categoriesMap.put("VBG", Features.Categories.cat4);
			} 
		}

		// --- VBN --- //
		if (vbn.getValue()!=null) {
			if (vbn.getValue().equals("Category 1")) {
				categoriesMap.put("VBN", Features.Categories.cat1);
			} else if (vbn.getValue().equals("Category 2")) {
				categoriesMap.put("VBN", Features.Categories.cat2);
			} else if (vbn.getValue().equals("Category 3")) {
				categoriesMap.put("VBN", Features.Categories.cat3);
			} else if (vbn.getValue().equals("Category 4")) {
				categoriesMap.put("VBN", Features.Categories.cat4);
			} 
		}
		
		// --- VBP --- //
		if (vbp.getValue()!=null) {
			if (vbp.getValue().equals("Category 1")) {
				categoriesMap.put("VBP", Features.Categories.cat1);
			} else if (vbp.getValue().equals("Category 2")) {
				categoriesMap.put("VBP", Features.Categories.cat2);
			} else if (vbp.getValue().equals("Category 3")) {
				categoriesMap.put("VBP", Features.Categories.cat3);
			} else if (vbp.getValue().equals("Category 4")) {
				categoriesMap.put("VBP", Features.Categories.cat4);
			} 
		}
		
		// --- VBZ --- //
		if (vbz.getValue()!=null) {
			if (vbz.getValue().equals("Category 1")) {
				categoriesMap.put("VBZ", Features.Categories.cat1);
			} else if (vbz.getValue().equals("Category 2")) {
				categoriesMap.put("VBZ", Features.Categories.cat2);
			} else if (vbz.getValue().equals("Category 3")) {
				categoriesMap.put("VBZ", Features.Categories.cat3);
			} else if (vbz.getValue().equals("Category 4")) {
				categoriesMap.put("VBZ", Features.Categories.cat4);
			} 
		}
		
		// --- WDT --- //
		if (wdt.getValue()!=null) {
			if (wdt.getValue().equals("Category 1")) {
				categoriesMap.put("WDT", Features.Categories.cat1);
			} else if (wdt.getValue().equals("Category 2")) {
				categoriesMap.put("WDT", Features.Categories.cat2);
			} else if (wdt.getValue().equals("Category 3")) {
				categoriesMap.put("WDT", Features.Categories.cat3);
			} else if (wdt.getValue().equals("Category 4")) {
				categoriesMap.put("WDT", Features.Categories.cat4);
			} 
		}
		
		// --- WP --- //
		if (wp.getValue()!=null) {
			if (wp.getValue().equals("Category 1")) {
				categoriesMap.put("WP", Features.Categories.cat1);
			} else if (wp.getValue().equals("Category 2")) {
				categoriesMap.put("WP", Features.Categories.cat2);
			} else if (wp.getValue().equals("Category 3")) {
				categoriesMap.put("WP", Features.Categories.cat3);
			} else if (wp.getValue().equals("Category 4")) {
				categoriesMap.put("WP", Features.Categories.cat4);
			} 
		}
		
		// --- WPS --- //
		if (wps.getValue()!=null) {
			if (wps.getValue().equals("Category 1")) {
				categoriesMap.put("WP$", Features.Categories.cat1);
			} else if (wps.getValue().equals("Category 2")) {
				categoriesMap.put("WP$", Features.Categories.cat2);
			} else if (wps.getValue().equals("Category 3")) {
				categoriesMap.put("WP$", Features.Categories.cat3);
			} else if (wps.getValue().equals("Category 4")) {
				categoriesMap.put("WP$", Features.Categories.cat4);
			} 
		}
		
		// --- WRB --- //
		if (wrb.getValue()!=null) {
			if (wrb.getValue().equals("Category 1")) {
				categoriesMap.put("WRB", Features.Categories.cat1);
			} else if (wrb.getValue().equals("Category 2")) {
				categoriesMap.put("WRB", Features.Categories.cat2);
			} else if (wrb.getValue().equals("Category 3")) {
				categoriesMap.put("WRB", Features.Categories.cat3);
			} else if (wrb.getValue().equals("Category 4")) {
				categoriesMap.put("WRB", Features.Categories.cat4);
			} 
		}
		
		// --- DT --- //
		if (dt.getValue()!=null) {
			if (dt.getValue().equals("Category 1")) {
				categoriesMap.put("DT", Features.Categories.cat1);
			} else if (dt.getValue().equals("Category 2")) {
				categoriesMap.put("DT", Features.Categories.cat2);
			} else if (dt.getValue().equals("Category 3")) {
				categoriesMap.put("DT", Features.Categories.cat3);
			} else if (dt.getValue().equals("Category 4")) {
				categoriesMap.put("DT", Features.Categories.cat4);
			} 
		}
		
		// --- DOT --- //
		// TODO the dot does not exist so far in the constants list.. check if it needs to be taken out/added
		if (dot.getValue()!=null) {
			if (dot.getValue().equals("Category 1")) {
				categoriesMap.put("DOT", Features.Categories.cat1);
			} else if (dot.getValue().equals("Category 2")) {
				categoriesMap.put("DOT", Features.Categories.cat2);
			} else if (dot.getValue().equals("Category 3")) {
				categoriesMap.put("DOT", Features.Categories.cat3);
			} else if (dot.getValue().equals("Category 4")) {
				categoriesMap.put("DOT", Features.Categories.cat4);
			} 
		}

		return categoriesMap;
	}
	
}
