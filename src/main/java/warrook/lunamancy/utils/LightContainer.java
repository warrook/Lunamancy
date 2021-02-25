package warrook.lunamancy.utils;

import warrook.lunamancy.utils.enums.Moonlight;

public interface LightContainer {
    Moonlight getLightType();
    float getAmount();
    float getCapacity();
}
