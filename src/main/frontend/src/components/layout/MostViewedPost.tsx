import React from 'react';
import { 
  Box, Typography,
} from '@mui/material';
import  { mostViewedItems } from "../data/BoardItems";
          
// 조회수 높은 게시글 컴포넌트
const MostViewedPost: React.FC = () => {

    const color : string[] = [
        '#FF9C8C', '#D2E866', '#FFDF8C', '#A6DEFF',
    ]

    return (
        <Box sx={{ display: 'flex', justifyContent:'space-between', marginTop:5 }}>
        {mostViewedItems.map((value, index) => {

            const LanguageImg = value.language ? (
                <img src={value.language} width="25" height="25"/>
                ) : (null); 

            return (
                <>
                <Box sx={{
                width: 250,
                height: 180,
                borderRadius:7,
                p:1,
                backgroundColor: color[index],
                m:1.2,
                position: 'relative',
                '&:hover': {
                opacity: [0.9, 0.8, 0.7],
                },
                }}>
                    {LanguageImg}
                    <Box sx={{
                    bottom:0,
                    position: "absolute", 
                    p:2,
                    }}>
                    <Typography sx={{fontSize:18}}>{value.title}</Typography>
                    </Box>
                </Box>
                </>
            )
        }
        )}
        </Box> 
    )
    }

export default MostViewedPost