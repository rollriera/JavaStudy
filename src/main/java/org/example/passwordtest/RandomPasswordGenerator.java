package org.example.passwordtest;

import org.passay.*;

public class RandomPasswordGenerator implements PasswordGenerator {

    public static final String ALLOWED_SPL_CHARACTERS = "!@#$%^&*()_+";

    public static final String ERROR_CODE = "ERRONEOUS_SPECIAL_CHARS";

    public String generatePassword(){
        org.passay.PasswordGenerator gen = new org.passay.PasswordGenerator();

        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return ALLOWED_SPL_CHARACTERS;
            }
        };
        CharacterRule sqlCharRule = new CharacterRule(specialChars);
        sqlCharRule.setNumberOfCharacters(2);

        // 0 ~ 12
        return gen.generatePassword((int) (Math.random() * 13), sqlCharRule, lowerCaseRule, upperCaseRule, digitRule);

    }
}
