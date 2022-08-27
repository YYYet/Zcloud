package com.chengzzz.zcloud.chain;


import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.dto.FileRequestDTO;
import com.chengzzz.zcloud.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.chain.impl.ContextBase;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件处理上下文
 *
 * @author Yet
 * @date 2022/08/27 21:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileContext extends ContextBase {

    BucketDTO bucketDTO;

    List<FileEntity> Files;

    FileRequestDTO fileRequest;

}
