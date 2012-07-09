-module(test).
-compile([export_all]).

%% Count words in a string.
count_words(Str)  ->
    count_words(Str, " ,;.").
count_words(Str, Separators)  ->                                                                                                                                              
    {_, Count} = lists:foldl(fun(Ch, {Pre, C}) ->
          InWord = not lists:member(Ch, Separators),
          case {Pre, InWord} of
             {false, true} -> {InWord, C + 1};
             _ -> {InWord, C}
          end
       end, {false, 0}, Str),
    Count.

%% Split string to words
split_words(Str) ->
    split_words(Str, " ,;.").
split_words(Str, Separators) ->
    split_words(Str, 0, [[]], false, Separators).
split_words([], Count, Words, _PreInWord, _Separators) ->
    {Count, lists:reverse( [ lists:reverse(Word) || Word <- Words, Word /= []] )};
split_words([Ch | Rest], Count, [Current | RestWords] = Words, PreInWord, Separators) ->
    InWord = not lists:member(Ch, Separators),
    case {PreInWord, InWord} of 
        {false, true} -> split_words(Rest, Count + 1, [[Ch] | Words], InWord, Separators);
        {true, true}  -> split_words(Rest, Count, [[Ch|Current] | RestWords], InWord, Separators);
        _             -> split_words(Rest, Count, Words, InWord, Separators)
    end. 
