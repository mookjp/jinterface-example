%%%-------------------------------------------------------------------
%% @doc erlang_jinterface_example top level supervisor.
%% @end
%%%-------------------------------------------------------------------

-module(erlang_jinterface_example_sup).

-behaviour(supervisor).

%% API
-export([start_link/0]).

%% Supervisor callbacks
-export([init/1]).

-define(SERVER, ?MODULE).
-define(ExecutablePath, "jinterface-example/bin/jinterface-example").

%%====================================================================
%% API functions
%%====================================================================

start_link() ->
    {ok, Sup} = supervisor:start_link({local, ?SERVER}, ?MODULE, []),
    io:format("supervisour: ~p~n", [Sup]),
    {ok, Child} = supervisor:start_child(Sup, []),
    io:format("hello_server: ~p~n", [Child]),
    JavaExecutablePath = filename:join([code:priv_dir(erlang_jinterface_example), ?ExecutablePath]),
    io:format("executable path: ~p~n", [JavaExecutablePath]),
    JavaNodePort = erlang:open_port(
        {spawn_executable, JavaExecutablePath},
        [{line, 1000}, use_stdio]
    ),
    io:format("java node started: ~p~n", [JavaNodePort]),
    {ok, self()}.

%%====================================================================
%% Supervisor callbacks
%%====================================================================

%% Child :: {Id,StartFunc,Restart,Shutdown,Type,Modules}
init([]) ->
    Child = [{
            hello_server, %% child_id
            {hello_server, start_link, []}, %% mfargs
            permanent, %% restart
            5000, %% shutdown,
            worker, %% type
            [hello_server] %% module
        }],
    {ok, {{simple_one_for_one, 10, 10}, Child}}.

%%====================================================================
%% Internal functions
%%====================================================================
