package com.github.bat333.stockroom.Infrastructure.Config;

import com.github.bat333.stockroom.useful.PartEntityMapper;
import com.github.bat333.stockroom.useful.SectorEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public PartEntityMapper partEntityMapper(SectorEntityMapper entityMapper){
        return new PartEntityMapper(entityMapper);
    }

    @Bean
    public SectorEntityMapper sectorEntityMapper(){
        return new SectorEntityMapper();
    }
}
