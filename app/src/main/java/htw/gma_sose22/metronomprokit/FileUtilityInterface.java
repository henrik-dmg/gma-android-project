package htw.gma_sose22.metronomprokit;

import java.io.File;

public interface FileUtilityInterface {

    void readFileAtPath(File file);
    void writeFileToPath(File file, Byte[] bytes);

}
