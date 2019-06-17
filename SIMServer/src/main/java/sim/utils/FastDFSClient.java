package sim.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

/**
 * The type Fast dfs client.
 */
@Component
public class FastDFSClient {

	@Autowired
	private FastFileStorageClient storageClient;

    /**
     * Upload base 64 string.
     *
     * @param file the file
     * @return the string
     * @throws IOException the io exception
     */
    public String uploadBase64(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
				"png", null);
		
		return storePath.getPath();
	}
	

}
