package ru.iteco.fmhandroid.ui.help;

import static ru.iteco.fmhandroid.ui.help.Helps.auth;
import static ru.iteco.fmhandroid.ui.help.Helps.changeIndex;
import static ru.iteco.fmhandroid.ui.help.Helps.clickAndFoundElement;
import static ru.iteco.fmhandroid.ui.help.Helps.clickElement;
import static ru.iteco.fmhandroid.ui.help.Helps.clickString;
import static ru.iteco.fmhandroid.ui.help.Helps.clickStringWait;
import static ru.iteco.fmhandroid.ui.help.Helps.closeKeyboard;
import static ru.iteco.fmhandroid.ui.help.Helps.foundElement;
import static ru.iteco.fmhandroid.ui.help.Helps.scroll;
import static ru.iteco.fmhandroid.ui.help.Helps.waitElement;

import ru.iteco.fmhandroid.R;

public class Steps {

    //Auth
    public static void authButton(){
        clickAndFoundElement(R.id.authorization_image_button);
    }

    public static void logOut(){
        clickString(R.string.log_out);
    }

    public static void authYes(){
        auth("login2", "password2");
    }

    public static void authError(){
        auth("login2", "Password2");
    }

    //Claim
    public static void executorChange() {
        clickElement(R.id.executor_drop_menu_auto_complete_text_view);
    }

    public static void claimInProgress() {
        clickStringWait(R.string.in_progress);
    }

    public static void claimInOpen() {
        clickStringWait(R.string.open);
    }

    public static void clickFilter() {
        clickElement(R.id.filters_material_button);
    }

    public static void filterInProgress() {
        clickElement(R.id.item_filter_in_progress);
    }

    public static void filterOk() {
        clickElement(R.id.claim_list_filter_ok_material_button);
    }

    public static void statusButton() {
        clickElement(R.id.status_processing_image_button);
    }

    public static void claimInCanceled() {
        clickStringWait(R.string.status_claim_canceled);
    }

    public static void claimEdit() {
        clickElement(R.id.edit_processing_image_button);
    }

    public static void closeKeybordInClaim() {
        closeKeyboard(R.id.executor_drop_menu_auto_complete_text_view);
    }

    public static void clickSave() {
        clickElement(R.id.save_button);
    }

    //Main
    public static void allNewsClick() {
        clickString(R.string.all_news);
    }

    public static void allNewsGood() {
        foundElement(R.id.all_news_cards_block_constraint_layout);
    }

    public static void chooseClaim() {
        changeIndex(R.id.claim_list_card);
    }

    public static void waitClaim() {
        waitElement(R.id.container_custom_app_bar_include_on_fragment_open_claim);
    }

    public static void allClaimsClick() {
        clickString(R.string.all_claims);
    }

    public static void allClaimsGood() {
        waitElement(R.id.container_custom_app_bar_include_on_fragment_list_claim);
    }

    public static void changeNew() {
        changeIndex(R.id.news_item_material_card_view);
    }

    public static void waitNew() {
        waitElement(R.id.news_item_description_text_view);
    }

    public static void missionButton() {
        clickElement(R.id.our_mission_image_button);
    }

    public static void missionWait() {
        waitElement(R.id.our_mission_title_text_view);
    }

    public static void startPage() {
        waitElement(R.id.login_text_input_layout);
    }

    //Menu

    public static void aboutWait() {
        waitElement(R.id.container_custom_app_bar_include_on_fragment_about);
    }

    public static void mainWait() {
        waitElement(R.id.container_custom_app_bar_include_on_fragment_main);
    }

    //News
    public static void newsWait() {
        waitElement(R.id.news_list_recycler_view);
    }

    public static void newFound(String text) {
        scroll(R.id.news_list_recycler_view,(text));
    }

    public static void clickPositive() {
        clickString(R.string.fragment_positive_button);
    }
}
