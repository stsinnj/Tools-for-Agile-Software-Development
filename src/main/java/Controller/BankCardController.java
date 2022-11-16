package Controller;


import Entity.BankCard;

import java.util.ArrayList;
import java.util.List;

public class BankCardController {
    List<BankCard> cardList = new ArrayList<>();
    String filePath = "resources/bankCard.csv";

    void loadCard() {
        cardList.clear();
        List<String> list = FileUtil.read(filePath);
        if (list.size() > 0) {
            for (String s : list) {
                String arr[] = s.split(",");
                if (arr.length == 4) {
                    BankCard card = new BankCard(arr[0], arr[1], arr[2], arr[3]);
                    cardList.add(card);
                }
            }
        }
    }


    /**
     * save a bankCard
     *
     * @param card
     * @return
     */
    public boolean saveCard(BankCard card) {
        loadCard();
        if (isExist(card.username)) {
            return false;
        }
        cardList.add(card);
        String saveContent = "";
        for (BankCard c : cardList) {
            saveContent += c.format() + "\n";
        }
        FileUtil.write(filePath, saveContent);
        return true;
    }


    public boolean isExist(String username) {
        loadCard();
        for (BankCard c : cardList) {
            if (c.username.equals(username)) {
                return true;
            }
        }
        return false;
    }


    public BankCard queryBankCardByName(String username) {
        loadCard();
        for (BankCard c : cardList) {
            if (c.username.equals(username)) {
                return c;
            }
        }
        return null;
    }
}
