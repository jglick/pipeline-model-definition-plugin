package org.jenkinsci.plugins.pipeline.modeldefinition.parser;

import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.stmt.Statement;

import javax.annotation.Nullable;

/**
 * Pattern match for the following Groovy construct:
 *
 * <pre><xmp>
 * foo(...) {
 *
 * }
 * </xmp></pre>
 *
 * @author Kohsuke Kawaguchi
 * @see ModelParser#matchBlockStatement(Statement)
 */
public class BlockStatementMatch {
    /**
     * ASTNode that matches the whole thing, which is a method invocation
     */
    public final MethodCallExpression whole;

    /**
     * Name of the method. In the above example, 'foo'
     */
    public final String methodName;

    /**
     * Method invocation arguments, including the last one that's a closure.
     */
    public final TupleExpression arguments;

    /**
     * Body of the block.
     *
     * @see ClosureExpression#getCode()
     */
    public final ClosureExpression body;

    public BlockStatementMatch(MethodCallExpression whole, String methodName, ClosureExpression body) {
        this.whole = whole;
        this.methodName = methodName;
        this.arguments = (TupleExpression)whole.getArguments(); // see MethodCallExpression.setArguments() that guarantee the success of this cast
        this.body = body;
    }

    /**
     * Gets the i-th argument if it exists, or return null.
     */
    public @Nullable
    Expression getArgument(int i) {
        if (i<arguments.getExpressions().size())
            return arguments.getExpression(i);
        else
            return null;
    }
}
