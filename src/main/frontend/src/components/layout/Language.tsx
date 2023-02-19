import React, { useState } from 'react';
import { Theme, useTheme } from '@mui/material/styles';
import {
    SelectChangeEvent,
    Select,
    Container,
    TextField,
    Button,
    Grid,
    FormControl,
    MenuItem,
    OutlinedInput,
    Chip, 
    Box, 
    Avatar
} from "@mui/material";
import java from "../data/java_logo.png";
import c from "../data/c_logo.png";
import javascript from "../data/js_logo.svg";

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;

const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
    },
  },
};

interface ChipData {
    key: number;
    name: string;
    logo?: string;
  }

const Language: React.FC = () => {
  const theme = useTheme();

  const [chipData, setChipData] = React.useState< ChipData[]>([
      { key: 0, name: 'C', logo: c  },
      { key: 1, name: 'Java', logo: java },
      { key: 2, name: 'JavaScript', logo: javascript },
      { key: 3, name: 'TypeScript' },
      { key: 4, name: 'Flutter' },
      { key: 5, name: 'Python' },
  ])

  const [SelectLang, setSelectLang] = React.useState<ChipData[]>([]);

  const onSelect = (event: SelectChangeEvent<typeof SelectLang>) => {
    const {
      target: { value },
    } = event;
    setSelectLang(
      SelectLang
    );
  }
  
  return (
    <Grid item>
      <FormControl sx={{ width: 600 }}>
        <Select
          labelId="chooseLanguage"
          id="choose-Language"
          multiple
          value={SelectLang}
          onChange={onSelect}
          input={<OutlinedInput id="select-multiple-chip" label="Chip" />}
          renderValue={(selected) => (
            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
              {selected.map((value) => (
                <Chip 
                avatar={<Avatar alt="icon" src={value.logo} />}
                key={value.key} label={value.name} />
              ))}
            </Box>
          )}
          MenuProps={MenuProps}
          size="small"
        >
          {chipData.map((language) => (
            <MenuItem
              key={language.key}
              value={language.name}
            >
              <Avatar   sx={{ width: 20, height: 20 }}
                     alt="icon" src={language.logo}/>
               {language.name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </Grid>
  );
}

export default Language;