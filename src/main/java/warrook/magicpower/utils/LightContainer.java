package warrook.magicpower.utils;

import warrook.magicpower.utils.enums.Moonlight;

public interface LightContainer {
    Moonlight getLightType();
    float getAmount();
    float getCapacity();
}
