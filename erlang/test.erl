count_words(Str)  ->
    count_words(Str, " ,;.").
count_words(Str, Separators)  ->                                                                                                                                              
    {_, Count} = lists:foldl(fun(Ch, {Pre, C}) ->
          InWord = not lists:member(Ch, Separators),
          case {Pre, InWord} of
             {false, true} -> {InWord, C + 1};
             _ -> {InWord, C}
          end
       end,
       {false, 0},
       Str),
    Count.
