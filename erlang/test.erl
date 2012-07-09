-module(test).
-compile([export_all]).
-import(lists,[reverse/1,member/2, foldl/3]).

%% N words in a string.
count_words(Str)  ->
    count_words(Str, " ,;.").
count_words(Str, Seps)  ->                                                                                                                                              
    {_, N} = lists:foldl(fun(Ch, {Pre, C}) ->
          InWord = not member(Ch, Seps),
          case {Pre, InWord} of
             {false, true} -> {InWord, C + 1};
             _ -> {InWord, C}
          end
       end, {false, 0}, Str),
    N.

%% Split string to words
tokens(Str, Seps) ->
    tokens(Str, [[]], false, Seps).
tokens([C|S], [Cs | RWs] = Ws, Pre, Seps) ->
    case {Pre, not member(C, Seps)} of 
        {false, true} -> tokens(S, [[C] | Ws], true, Seps);
        {true, true}  -> tokens(S, [[C|Cs] | RWs], true, Seps);
        _             -> tokens(S, Ws, false, Seps)
    end;
tokens([], Ws, _Pre, _Seps) ->
    reverse( [ reverse(W) || W <- Ws, W /= []]).
