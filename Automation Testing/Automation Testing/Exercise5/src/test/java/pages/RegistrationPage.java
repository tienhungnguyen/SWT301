package pages;

import org.openqa.selenium.*;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By emailField = By.id("userEmail");
    private By mobileField = By.id("userNumber");
    private By currentAddressField = By.id("currentAddress");
    private By submitButton = By.id("submit");

    // Gender radio buttons
    private By maleGender = By.xpath("//label[@for='gender-radio-1']");
    private By femaleGender = By.xpath("//label[@for='gender-radio-2']");
    private By otherGender = By.xpath("//label[@for='gender-radio-3']");

    // Date of Birth
    private By dateOfBirthInput = By.id("dateOfBirthInput");
    private By monthDropdown = By.className("react-datepicker__month-select");
    private By yearDropdown = By.className("react-datepicker__year-select");

    // Subjects
    private By subjectsInput = By.id("subjectsInput");

    // Hobbies
    private By sportsHobby = By.xpath("//label[@for='hobbies-checkbox-1']");
    private By readingHobby = By.xpath("//label[@for='hobbies-checkbox-2']");
    private By musicHobby = By.xpath("//label[@for='hobbies-checkbox-3']");

    // File upload
    private By pictureUpload = By.id("uploadPicture");

    // State and City - Updated selectors
    private By stateContainer = By.id("state");
    private By stateInput = By.xpath("//div[@id='state']//input");
    private By cityContainer = By.id("city");
    private By cityInput = By.xpath("//div[@id='city']//input");

    // Success modal
    private By successModal = By.className("modal-content");
    private By modalTitle = By.id("example-modal-sizes-title-lg");
    private By closeButton = By.id("closeLargeModal");

    // Actions
    public void navigate() {
        navigateTo("https://demoqa.com/automation-practice-form");
        waitForPageReady();
        removeAds(); // Remove ads immediately after loading
    }

    public void fillBasicInfo(String firstName, String lastName, String email, String mobile) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(emailField, email);
        type(mobileField, mobile);
    }

    public void selectGender(String gender) {
        removeAds(); // Remove ads before clicking
        switch (gender.toLowerCase()) {
            case "male":
                forceClick(maleGender);
                break;
            case "female":
                forceClick(femaleGender);
                break;
            case "other":
                forceClick(otherGender);
                break;
        }
    }

    public void setDateOfBirth(String day, String month, String year) {
        click(dateOfBirthInput);
        selectByText(monthDropdown, month);
        selectByText(yearDropdown, year);

        // Click on the day
        By dayLocator = By.xpath("//div[contains(@class,'react-datepicker__day') and text()='" + day + "']");
        click(dayLocator);
    }

    public void addSubject(String subject) {
        scrollToElement(subjectsInput);
        type(subjectsInput, subject);
        driver.findElement(subjectsInput).sendKeys(Keys.TAB);
    }

    public void selectHobbies(String[] hobbies) {
        removeAds(); // Remove ads before clicking hobbies
        for (String hobby : hobbies) {
            scrollToElement(getHobbyLocator(hobby.toLowerCase()));
            try {
                Thread.sleep(300); // Small delay between hobby selections
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            switch (hobby.toLowerCase()) {
                case "sports":
                    forceClick(sportsHobby);
                    break;
                case "reading":
                    forceClick(readingHobby);
                    break;
                case "music":
                    forceClick(musicHobby);
                    break;
            }
        }
    }

    private By getHobbyLocator(String hobby) {
        switch (hobby) {
            case "sports": return sportsHobby;
            case "reading": return readingHobby;
            case "music": return musicHobby;
            default: return sportsHobby;
        }
    }

    public void uploadPicture(String filePath) {
        driver.findElement(pictureUpload).sendKeys(filePath);
    }

    public void fillAddress(String address) {
        scrollToElement(currentAddressField);
        type(currentAddressField, address);
    }

    public void selectStateAndCity(String state, String city) {
        removeAds(); // Remove ads before state/city selection

        // Scroll to state section
        scrollToElement(stateContainer);

        try {
            Thread.sleep(1000); // Wait for ads to be removed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Select state using input method (more reliable)
        try {
            // Click state container
            forceClick(stateContainer);

            // Type state name
            WebElement stateInputElement = driver.findElement(stateInput);
            stateInputElement.sendKeys(state);
            stateInputElement.sendKeys(Keys.ENTER);

            Thread.sleep(500); // Wait for city dropdown to populate

            // Click city container
            forceClick(cityContainer);

            // Type city name
            WebElement cityInputElement = driver.findElement(cityInput);
            cityInputElement.sendKeys(city);
            cityInputElement.sendKeys(Keys.ENTER);

        } catch (Exception e) {
            System.out.println("Error selecting state/city: " + e.getMessage());
            // Fallback to JavaScript selection
            selectStateAndCityJS(state, city);
        }
    }

    private void selectStateAndCityJS(String state, String city) {
        // JavaScript fallback for state selection
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('#state input').value = arguments[0];" +
                        "document.querySelector('#state input').dispatchEvent(new Event('input', { bubbles: true }));" +
                        "setTimeout(() => {" +
                        "  const stateOption = [...document.querySelectorAll('#state div')].find(el => el.textContent === arguments[0]);" +
                        "  if(stateOption) stateOption.click();" +
                        "}, 100);", state
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // JavaScript fallback for city selection
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('#city input').value = arguments[0];" +
                        "document.querySelector('#city input').dispatchEvent(new Event('input', { bubbles: true }));" +
                        "setTimeout(() => {" +
                        "  const cityOption = [...document.querySelectorAll('#city div')].find(el => el.textContent === arguments[0]);" +
                        "  if(cityOption) cityOption.click();" +
                        "}, 100);", city
        );
    }

    public void submitForm() {
        scrollToElement(submitButton);
        removeAds(); // Final ad removal before submit

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        forceClick(submitButton);
    }

    public boolean isSuccessModalDisplayed() {
        try {
            return waitForVisibility(successModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getModalTitle() {
        return getText(modalTitle);
    }

    public void closeModal() {
        if (isSuccessModalDisplayed()) {
            click(closeButton);
        }
    }

    // Complete registration method with better error handling
    public void completeRegistration(String firstName, String lastName, String email,
                                     String gender, String mobile, String day, String month,
                                     String year, String subject, String[] hobbies,
                                     String address, String state, String city) {
        try {
            fillBasicInfo(firstName, lastName, email, mobile);
            Thread.sleep(300);

            selectGender(gender);
            Thread.sleep(300);

            setDateOfBirth(day, month, year);
            Thread.sleep(300);

            addSubject(subject);
            Thread.sleep(300);

            selectHobbies(hobbies);
            Thread.sleep(300);

            fillAddress(address);
            Thread.sleep(300);

            selectStateAndCity(state, city);
            Thread.sleep(500);

            submitForm();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        }
    }
}
