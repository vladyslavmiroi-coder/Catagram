package com.zoi4erom.catagram.service;

public interface CrudService<Create, Update, Read, ID>{
        void create (Create createDTO);
        Read findById (ID id);
        Read update (Update updateDTO);
        void delete (ID id);
}
